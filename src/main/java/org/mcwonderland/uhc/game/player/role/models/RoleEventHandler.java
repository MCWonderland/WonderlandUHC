package org.mcwonderland.uhc.game.player.role.models;

import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface RoleEventHandler {

    void onGamingJoin(UHCJoinEvent e);

    void onStartingJoin(UHCJoinEvent e);

    void onQuit(PlayerQuitEvent e);
}
