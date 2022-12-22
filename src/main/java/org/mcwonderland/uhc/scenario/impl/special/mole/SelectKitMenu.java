package org.mcwonderland.uhc.scenario.impl.special.mole;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SelectKitMenu {
    private final Inventory inventory = Bukkit.createInventory(null, 18, ChatColor.GOLD + "選擇一個職業");

    public void open(Player player) {
        inventory.clear();

        addScout(inventory);
        addPhoenix(inventory);
        addTeleporter(inventory);

        player.openInventory(inventory);
    }

    private static void addTeleporter(Inventory inventory) {
        // Teleporter
        ItemStack teleport = new ItemStack(Material.ENDER_PEARL);
        ItemMeta teleportMeta = teleport.getItemMeta();
        teleportMeta.setDisplayName(ChatColor.AQUA + "傳送者");
        teleportMeta.setLore(Arrays.asList(ChatColor.WHITE + "終界珍珠x6"));
        teleport.setItemMeta(teleportMeta);
        inventory.setItem(2, teleport);
    }

    private static void addPhoenix(Inventory inventory) {
        // Phoenix
        ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta phoenixMeta = phoenix.getItemMeta();
        phoenixMeta.setDisplayName(ChatColor.AQUA + "不死鳳凰");
        phoenixMeta.setLore(Arrays.asList(ChatColor.GOLD.toString() + ChatColor.BOLD + "特殊" + ChatColor.WHITE + "胡蘿蔔x1 " + ChatColor.GRAY + "(吃了會獲得 3排吸收(60顆愛心) 90秒)"));
        phoenix.setItemMeta(phoenixMeta);
        inventory.setItem(1, phoenix);
    }

    private static void addScout(Inventory inventory) {
        // Scout
        ItemStack scout = new ItemStack(Material.FEATHER);
        ItemMeta scoutMeta = scout.getItemMeta();
        scoutMeta.setDisplayName(ChatColor.AQUA + "偵查兵");
        scoutMeta.setLore(Arrays.asList(ChatColor.WHITE + "永久速度I"));
        scout.setItemMeta(scoutMeta);
        inventory.setItem(0, scout);
    }

    public Inventory getMenuInventory() {
        return inventory;
    }

}
