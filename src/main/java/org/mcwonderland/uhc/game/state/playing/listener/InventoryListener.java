package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.mineacademy.fo.menu.Menu;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inv = event.getInventory();
        InventoryHolder holder = inv.getHolder();
        Player player = ( Player ) event.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (holder instanceof Minecart) {
            if (uhcPlayer.getRoleName() == RoleName.SPECTATOR) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = ( Player ) e.getWhoClicked();
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (uhcPlayer.getRoleName() == RoleName.SPECTATOR && Menu.getMenu(player) == null) {
            e.setCancelled(true);
        }
    }


}
