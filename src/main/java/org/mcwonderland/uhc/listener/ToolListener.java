package org.mcwonderland.uhc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.tool.Tool;

public class ToolListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (isTool(e.getCurrentItem()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if (isTool(e.getItemDrop().getItemStack()))
            e.setCancelled(true);
    }


    private boolean isTool(ItemStack itemStack) {
        return Tool.getTool(itemStack) != null;
    }
}
