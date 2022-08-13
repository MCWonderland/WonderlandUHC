package org.mcwonderland.uhc.game.player.role.spectator;

import org.mcwonderland.uhc.game.player.role.models.RoleEventHandler;
import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class RoleSpectatorEvents implements RoleEventHandler {

    @Override
    public void onGamingJoin(UHCJoinEvent e) {
        Player player = e.getPlayer();
        player.teleport(UHCWorldUtils.getZeroZero());
    }

    @Override
    public void onStartingJoin(UHCJoinEvent e) {
        Player player = e.getPlayer();
        player.teleport(UHCWorldUtils.getZeroZero());
    }

    @Override
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
}
