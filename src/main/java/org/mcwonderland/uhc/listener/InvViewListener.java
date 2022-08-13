package org.mcwonderland.uhc.listener;

import org.mcwonderland.uhc.menu.impl.InventoryViewer;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InvViewListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player player = e.getPlayer();

            if (GameUtils.isStaff(player)) {
                Player target = (( Player ) e.getRightClicked());

                new InventoryViewer(target).displayTo(player);
            }
        }
    }

}
