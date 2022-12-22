package org.mcwonderland.uhc.scenario.impl.special.mole;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.Objects;

@RequiredArgsConstructor
public class SelectKitMenuListener implements Listener {

    private final SelectKitMenu menu;
    private final ScenarioMole mole;

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().getSize() == menu.getMenuInventory().getSize()) {
            event.setCancelled(true);

            // spawn the chest
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();
            Block block = player.getWorld().getBlockAt(x, y, z);
            block.setType(Material.CHEST);
            BlockState blockState = block.getState();
            Chest chest = (Chest) blockState;
            Inventory inventory = chest.getBlockInventory();

            switch (event.getSlot()) {
                case 0: {
                    // Scout
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0));
                    Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "偵查兵").getMessages());
                    break;
                }
                case 1: {
                    // Phoenix
                    ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
                    ItemMeta phoenixMeta = phoenix.getItemMeta();
                    phoenixMeta.setDisplayName(ChatColor.GOLD + "不死鳳凰");
                    phoenix.setItemMeta(phoenixMeta);
                    inventory.addItem(phoenix);
                    Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "不死鳳凰").getMessages());
                    break;
                }
                case 2:
                    // Teleporter
                    inventory.addItem(new ItemStack(Material.ENDER_PEARL, 6));
                    Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "傳送者").getMessages());
                    break;
                default:
                    return;
            }

            mole.markKitSelected(player);
            player.closeInventory();
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1800, 14, false);
        if (item.hasItemMeta())
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "不死鳳凰"))
                effect.apply(player);
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        PotionEffect effect = new PotionEffect(PotionEffectType.HUNGER, 100, 1);
        if (item.hasItemMeta())
            if (item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "不死鳳凰"))
                effect.apply(player);
    }

}
