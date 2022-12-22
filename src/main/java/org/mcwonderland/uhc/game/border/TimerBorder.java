package org.mcwonderland.uhc.game.border;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameTimerRunnable;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.teleport.TeleportMode;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.BorderUtil;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TimerBorder implements Border {
    public boolean TELEPORTING = false;
    private Integer commandSize = null;
    public int borSize = 0;

    @Override
    public void onCountdown(int untilShrink) {
        World uhcWorld = UHCWorldUtils.getWorld();

        Integer nextBorder = getNextBorder();

        if (nextBorder == null)
            return;

        if (untilShrink < 10 && untilShrink % 2 == 0) {
            if (borSize < Settings.Border.BEDROCK_BORDER_HEIGHT) {
                BorderUtil.generateBorder(uhcWorld, nextBorder);
                borSize++;
            }

            getTpMode().preGenerateTeleportLocation(nextBorder);
        }
    }

    @Override
    public String getCountdownBroadcast() {
        return Messages.CountDown.Border.Timer.ANNOUNCE
                .replace("{size}", getNextBorder() + "");
    }

    @Override
    public String getShrinkMessage() {
        return Messages.CountDown.Border.Timer.REDUCED
                .replace("{size}", Game.getGame().getCurrentBorder() + "");
    }

    @Override
    public void shrinkToNextSize() {
        Game game = Game.getGame();

        if (TELEPORTING || !hasNextBorder())
            return;

        TELEPORTING = true;
        int borderSize = getNextBorder();
        game.setCurrentBorder(borderSize);
        removeBiggerBorder(borderSize);

        teleportPlayers(() -> {
            borSize = 0;
            TELEPORTING = false;
            commandSize = null;

            BorderUtil.setBorders(UHCWorldUtils.getWorld(), borderSize);

            BorderUtil.clearBorderCaches();
            getTpMode().clearLocationCache();


            if (hasNextBorder()) {
                Integer after = GameTimerRunnable.totalSecond + Settings.Border.BORDER_TIMER_AFTER_FIRST_SHRINK;
                game.getSettings().getTimer().setBorderShrinkTime(after);
            }
        });
    }

    @Override
    public void onCommand(int size) {
        commandSize = size;
    }

    private boolean hasNextBorder() {
        return getNextBorder() != null;
    }

    private Integer getNextBorder() {
        if (commandSize != null)
            return commandSize;

        return getBorderSizes().lower(Game.getGame().getCurrentBorder());
    }

    private void removeBiggerBorder(int border) {
        TreeSet<Integer> sizes = getBorderSizes();

        while (!sizes.isEmpty() && sizes.last() >= border)
            sizes.pollLast();
    }

    private TreeSet<Integer> getBorderSizes() {
        return Settings.Border.BORDER_TP_SIZES;
    }

    private void teleportPlayers(Runnable callback) {
        teleportSpectatorAndStaffs();
        teleportGamingPlayers(callback);
    }

    private void teleportSpectatorAndStaffs() {
        Collection<UHCPlayer> toTeleport = UHCPlayers.getOnline(UHCPlayer::isDead);
        Location zeroZero = UHCWorldUtils.getZeroZero();

        for (UHCPlayer uhcPlayer : toTeleport) {
            Player player = uhcPlayer.getPlayer();

            if (!BorderUtil.isInBorder(player.getLocation()))
                player.teleport(zeroZero);
        }
    }

    private void teleportGamingPlayers(Runnable callBack) {
        Queue<UHCTeam> uTeams = getQueue();

        new TeleportTask(uTeams, callBack).runTaskTimer(WonderlandUHC.getInstance(), 0, 2);
    }

    private TeleportMode getTpMode() {
        return Settings.Game.TELEPORT_TYPE.getMode();
    }


    private class TeleportTask extends BukkitRunnable {
        private Queue<UHCTeam> uhcTeams;
        private Runnable callback;

        public TeleportTask(Queue<UHCTeam> uTeams, Runnable callback) {
            this.uhcTeams = uTeams;
            this.callback = callback;
        }

        public void run() {
            UHCTeam team = uhcTeams.poll();

            if (team == null) {
                callback.run();
                this.cancel();
                return;
            }

            if (team.isEliminate()) {
                team.getAliveEntities().forEach(entity -> entity.teleport(UHCWorldUtils.getZeroZero()));
                return;
            }

            if (hasMemberOutOfTheBorder(team))
                getTpMode().teleport(team);
            else
                run();
        }
    }

    private Queue<UHCTeam> getQueue() {
        ArrayList<UHCTeam> teams = Lists.newArrayList(UHCTeam.getAliveTeams());
        Collections.shuffle(teams);

        return Queues.newLinkedBlockingQueue(teams);
    }

    private boolean hasMemberOutOfTheBorder(UHCTeam team) {
        return team.getAliveEntities().stream()
                .anyMatch(livingEntity -> !BorderUtil.isInBorder(livingEntity.getLocation()));
    }
}
