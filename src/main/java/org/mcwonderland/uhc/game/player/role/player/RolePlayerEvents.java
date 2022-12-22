package org.mcwonderland.uhc.game.player.role.player;

import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameManager;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.role.models.RoleEventHandler;
import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;
import org.mcwonderland.uhc.game.timer.Timers;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.fo.Common;

public class RolePlayerEvents implements RoleEventHandler {

    @Override
    public void onGamingJoin(UHCJoinEvent e) {
        removeRelogAndRestorePlayer(e.getUhcPlayer());
        e.getPlayer().setGameMode(GameMode.SURVIVAL);
    }

    @Override
    public void onStartingJoin(UHCJoinEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();
        UHCTeam team = uhcPlayer.getTeam();

        boolean firstJoin = (team == null);

        if (firstJoin) {
            team = UHCTeam.createTeamIfNotExist(uhcPlayer);
            Timers.SCATTER.scatter(team);
        } else {
            Player player = uhcPlayer.getPlayer();
            Location location = Timers.SCATTER.getScatterLocation(team);

            if (location == null)
                player.teleport(UHCWorldUtils.getLobbySpawn());
            else {
                player.teleport(location);
                GameManager.freeze(player);
            }
        }
    }

    @Override
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (isNoRelog())
            player.setHealth(0);
        else {
            Integer relogInMinutes = Settings.CombatRelog.RELOG_IN_MINUTES;

            CombatRelog.setCombatRelog(uhcPlayer);

            e.setQuitMessage(Messages.Game.PLAYER_DISCONNECT
                    .replace("{player}", uhcPlayer.getTeam().getChatFormat() + uhcPlayer.getName())
                    .replace("{time}", relogInMinutes + ""));
        }
    }

    private boolean isNoRelog() {
        return !Settings.CombatRelog.ENABLED
                || (Game.getGame().isPvpEnabled() && !Settings.CombatRelog.RELOG_AFTER_PVP_ENABLED);
    }

    private void removeRelogAndRestorePlayer(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();

        CombatRelog z = CombatRelog.get(uhcPlayer);
        LivingEntity relogEntity = z.getEntity();
        Extra.copyHealth(relogEntity, player);
        z.getInventoryContent().setContents(player);
        player.setLevel(z.getLevel());
        player.setExp(z.getXp());
        player.addPotionEffects(z.getEntity().getActivePotionEffects());

        Common.runLater(1, () -> {
            if (!uhcPlayer.isOnline())
                return;

            relogEntity.remove();
            player.teleport(relogEntity.getLocation());
            z.remove();
        });
    }
}
