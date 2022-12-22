package org.mcwonderland.uhc.game.state.share;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class CommonListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPsychic(PlayerInteractEvent e) {
        Action action = e.getAction();
        if (action == Action.PHYSICAL)
            e.setCancelled(true);
    }
}
