package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemListener implements Listener {

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {
        if (!GameUtils.isGamingPlayer(e.getPlayer()))
            e.setCancelled(true);
    }


    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(e.getPlayer());

        if (uhcPlayer.isDead())
            e.setCancelled(true);
    }
}
