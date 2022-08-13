package org.mcwonderland.uhc.game.player.role.models;

import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EmptyEventHandler implements RoleEventHandler {

    @Override
    public final void onGamingJoin(UHCJoinEvent e) {

    }

    @Override
    public void onStartingJoin(UHCJoinEvent e) {

    }

    @Override
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

}
