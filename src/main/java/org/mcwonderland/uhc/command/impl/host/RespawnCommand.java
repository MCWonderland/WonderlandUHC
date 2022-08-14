package org.mcwonderland.uhc.command.impl.host;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.api.event.player.UHCPlayerRespawnedEvent;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.DeathPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.settings.sub.UHCItemSettings;
import org.mcwonderland.uhc.model.InventoryContent;
import org.mcwonderland.uhc.model.InvinciblePlayer;
import org.mcwonderland.uhc.model.Teleporter;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.util.*;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.List;

/**
 * 2019-11-27 下午 01:05
 */
public class RespawnCommand extends SimpleCommand {
    public RespawnCommand(String label) {
        super(label);

        setMinArguments(1);
        setUsage("<玩家>");
        setDescription("復活玩家。");
        setPermission(UHCPermission.COMMAND_RESPAWN.toString());
    }

    @Override
    protected void onCommand() {
        Player target = findPlayer(args[0]);
        CommandHelper.checkGameStarted();
        checkBoolean(!GameUtils.isGamingPlayer(target), CommandSettings.Respawn.IS_PLAYING);

        new RespawnHandler(target).respawn();
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1)
            return completeLastWord(Common.getPlayerNames());

        return super.tabComplete();
    }

    class RespawnHandler {
        private final Player target;
        private final UHCPlayer targetUHCPlayer;
        private Location toTeleport;
        private InventoryContent inv;
        private int level = 0;
        private float exp = 0;

        public RespawnHandler(Player target) {
            this.target = target;
            this.targetUHCPlayer = UHCPlayer.getUHCPlayer(target);
        }

        private void respawn() {
            handleRespawnVaribles();
            handleData();
            restoreAndTeleport();
            InvinciblePlayer.addInvincible(targetUHCPlayer, Settings.Game.RESPAWN_INVINCIBLE_TIME);

            Chat.send(target, CommandSettings.Respawn.RESPAWNED);
            Chat.broadcast(CommandSettings.Respawn.BROADCAST
                    .replace("{mod}", getPlayer().getName())
                    .replace("{player}", target.getName()));
            Extra.sound(target, Sounds.Commands.RESPAWN);

            Common.callEvent(new UHCPlayerRespawnedEvent(targetUHCPlayer));
        }

        private void restoreAndTeleport() {
            Extra.comepleteClear(target);
            target.setGameMode(GameMode.SURVIVAL);
            inv.setContents(target);
            target.setLevel(level);
            target.setExp(exp);
            DaTouNMS.getCommonNMS().setCanPickupExp(target, true);
            target.spigot().setCollidesWithEntities(true);
            target.teleport(toTeleport);
        }

        private void handleRespawnVaribles() {
            Game game = Game.getGame();
            DeathPlayer deathPlayer = DeathPlayer.getDeathPlayer(targetUHCPlayer);

            if (deathPlayer != null) {
                inv = deathPlayer.getInvContent();
                level = deathPlayer.getLevel();
                exp = deathPlayer.getExp();
                toTeleport = getRespawnLocation(deathPlayer, game.getCurrentBorder());
            } else {
                UHCItemSettings itemSettings = Game.getSettings().getItemSettings();
                inv = itemSettings.getCustomInventoryItems();
                toTeleport = Teleporter.getRandomTp(UHCWorldUtils.getWorld(), game.getCurrentBorder());
            }
        }

        private Location getRespawnLocation(DeathPlayer dp, int border) {
            /*
            如果在地獄，要判斷兩個點
            1. 地獄是否關閉了
            2. 如果地獄沒關，而且地獄的關閉方式是邊界慢慢縮，則得判斷玩家座標是不是已經在邊界外了
             */

            Location toTeleport;
            if (dp.getLocation().getWorld() == UHCWorldUtils.getNether()
                    && (!GameUtils.isNetherOn() || (Settings.Border.INCLUDE_18_BORDER
                    && !BorderUtil.isInBorder(dp.getLocation(), BorderUtil.getMoveBorder(UHCWorldUtils.getNether()))))) {
                toTeleport = Teleporter.getRandomTp(UHCWorldUtils.getWorld(), border);
            } else if (!BorderUtil.isInBorder(dp.getLocation(), border)) {
                toTeleport = Teleporter.getRandomTp(UHCWorldUtils.getWorld(), border);
            } else {
                toTeleport = dp.getLocation();
            }
            return toTeleport;
        }

        private void handleData() {
            Game.getGame().getWhiteList().add(target);
            targetUHCPlayer.changePlayerRole();
        }
    }
}
