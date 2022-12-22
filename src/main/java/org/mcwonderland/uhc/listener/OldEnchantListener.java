package org.mcwonderland.uhc.listener;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompMaterial;

public class OldEnchantListener implements Listener {

    @EventHandler
    public void prepareItemEnchant(PrepareItemEnchantEvent e) {
        if (Settings.OldEnchant.RANDOM_ENCHANT)
            DaTouNMS.getEnchantHandler().randomizeSeed(e);
        if (Settings.OldEnchant.OLD_ENCHANT_COST)
            DaTouNMS.getEnchantHandler().oldEnchantCosts(e);
        if (Settings.OldEnchant.HIDE_ENCHANT)
            DaTouNMS.getEnchantHandler().hideEnchants(e);
    }

    @EventHandler
    public void lapisClickEvent(InventoryClickEvent e) {
        if (Settings.OldEnchant.LAPIS
                && isEnchantInventory(e.getClickedInventory())
                && e.getRawSlot() == 1)
            e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        fillLapis(event.getInventory());
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();

        if (Settings.OldEnchant.LAPIS && isEnchantInventory(inventory))
            inventory.setItem(1, null);
    }

    @EventHandler
    public void enchantItem(EnchantItemEvent e) {
        if (Settings.OldEnchant.LAPIS || Settings.OldEnchant.OLD_ENCHANT_COST) {
            Player enchanter = e.getEnchanter();
            int tempLevel = enchanter.getLevel();
            int levelCost = e.getExpLevelCost();


            Common.runLater(1, () -> {
                if (Settings.OldEnchant.OLD_ENCHANT_COST) {
                    try {
                        enchanter.setLevel(Math.max(tempLevel - levelCost, 0));
                    } catch (NullPointerException ex) {

                    }
                }

                fillLapis(e.getInventory());
            });
        }
    }

    private boolean isEnchantInventory(Inventory i) {
        return i != null && i.getType() == InventoryType.ENCHANTING;
    }

    public void fillLapis(Inventory inventory) {
        if (Settings.OldEnchant.LAPIS && inventory.getType() == InventoryType.ENCHANTING) {
            inventory.setItem(1, CompMaterial.LAPIS_LAZULI.toItem(64));
        }
    }
}
