package org.mcwonderland.uhc.model;

import com.google.common.collect.Maps;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.remain.Remain;

import java.util.HashMap;
import java.util.Map;

public class InvinciblePlayer {
    private static final Map<UHCPlayer, InvinciblePlayer> invinciblePlayers = new HashMap<>();

    public static void startTask() {
        Common.runTimerAsync(20 * 1, new InvinciblePlayerTask());
    }

    private int time;

    private InvinciblePlayer(int time) {
        this.time = time;
    }

    public static void addInvincible(UHCPlayer uhcPlayer, int time) {
        if (isNeedToAdd(uhcPlayer, time)) {
            invinciblePlayers.put(uhcPlayer, new InvinciblePlayer(time));

            Chat.send(uhcPlayer.getPlayer(), TimeUtil.replacePlaceholders(Messages.Game.NoClean.OBTAINED, time));
        }
    }

    private static boolean isNeedToAdd(UHCPlayer uhcPlayer, int time) {
        InvinciblePlayer invinciblePlayer = InvinciblePlayer.getInvinciblePlayer(uhcPlayer);

        return invinciblePlayer == null || invinciblePlayer.time < time;
    }

    public static void removeInvincible(UHCPlayer uhcPlayer) {
        InvinciblePlayer removed = invinciblePlayers.remove(uhcPlayer);

        if (removed != null)
            tellInvincibleEnd(uhcPlayer.getPlayer());
    }

    private static void tellInvincibleEnd(Player player) {
        Chat.send(player, Messages.Game.NoClean.END);
        Extra.sound(player, Sounds.Game.INVINCIBLE_END);
    }

    public static InvinciblePlayer getInvinciblePlayer(UHCPlayer uhcPlayer) {
        return invinciblePlayers.get(uhcPlayer);
    }

    public static boolean isInvincible(UHCPlayer uhcPlayer) {
        return getInvinciblePlayer(uhcPlayer) != null;
    }

    private static class InvinciblePlayerTask extends BukkitRunnable {

        public void run() {
            Maps.newHashMap(invinciblePlayers).forEach((uhcPlayer, invinciblePlayer) -> {
                invinciblePlayer.time--;

                if (invinciblePlayer.time <= 0)
                    removeInvincible(uhcPlayer);

                if (Settings.Misc.NO_CLEAN_ACTION_BAR)
                    sendActionBar(uhcPlayer, invinciblePlayer.time);
            });
        }

        private void sendActionBar(UHCPlayer uhcPlayer, int time) {
            Player player = uhcPlayer.getPlayer();

            if (time > 0)
                Remain.sendActionBar(player, TimeUtil.replacePlaceholders(Messages.Game.NoClean.ACTION_BAR, time));
            else
                Remain.sendActionBar(player, Messages.Game.NoClean.ACTION_BAR_END);
        }
    }
}


