package org.mcwonderland.uhc.command.scenarioMole;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MoleKitCommand extends SimpleSubCommand implements Listener {

    private final Set<Player> kitReceivers = new HashSet<>();

    public MoleKitCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("");
        setMinArguments(0);
        setDescription("選擇想要的職業");
    }

    @Override
    protected void onCommand() {
        UHCPlayer player = UHCPlayer.getUHCPlayer(getPlayer());

//        if (ScenarioMole.getMoleList().contains(player) && player.isAlive()) chooseKit();
//        else Chat.send(getSender(), ScenarioMole.getMoleCommandDeniedMessage());

        if (!kitReceivers.contains(getPlayer()))
            chooseKit();
        else
            Chat.send(getSender(), CommandSettings.Mole.KIT_TWICE);
    }

    public final Inventory kitSelector = Bukkit.createInventory(getPlayer(), 9, ChatColor.GOLD + "選擇一個職業");

    private void chooseKit() {
        // Scout
        ItemStack scout = new ItemStack(Material.FEATHER);
        ItemMeta scoutMeta = scout.getItemMeta();
        scoutMeta.setDisplayName(ChatColor.AQUA + "偵查兵");
        scoutMeta.setLore(Arrays.asList(ChatColor.WHITE + "永久速度I"));
        scout.setItemMeta(scoutMeta);
        kitSelector.setItem(0, scout);

        // Phoenix
        ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta phoenixMeta = phoenix.getItemMeta();
        phoenixMeta.setDisplayName(ChatColor.AQUA + "不死鳳凰");
        phoenixMeta.setLore(Arrays.asList(ChatColor.GOLD.toString() + ChatColor.BOLD + "特殊" + ChatColor.WHITE + "胡蘿蔔x1 " + ChatColor.GRAY + "(吃了會獲得 吸收9(60顆愛心) 90秒)"));
        phoenix.setItemMeta(phoenixMeta);
        kitSelector.setItem(1, phoenix);

        // Teleporter
        ItemStack teleport = new ItemStack(Material.ENDER_PEARL);
        ItemMeta teleportMeta = teleport.getItemMeta();
        teleportMeta.setDisplayName(ChatColor.AQUA + "傳送者");
        teleportMeta.setLore(Arrays.asList(ChatColor.WHITE + "終界珍珠x6"));
        teleport.setItemMeta(teleportMeta);
        kitSelector.setItem(2, teleport);


        getPlayer().openInventory(kitSelector);
    }

    //todo onClick 沒有反應
    @EventHandler
    private void onClick(InventoryClickEvent e) {
        Chat.broadcast("RRRRRRRR");
        if (e.getClickedInventory() == kitSelector && e.getCurrentItem() != null) {
            e.setCancelled(true);

            // spawn the chest
            int x = getPlayer().getLocation().getBlockX();
            int y = getPlayer().getLocation().getBlockY() - 1;
            int z = getPlayer().getLocation().getBlockZ();
            Block block = getPlayer().getWorld().getBlockAt(x, y, z);
            block.setType(Material.CHEST);
            BlockState blockState = block.getState();
            Chest chest = (Chest) blockState;
            Inventory inventory = chest.getBlockInventory();

            switch (e.getSlot()) {
                case 0: {
                    // Scout
                    getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
                    Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "偵查兵").getMessages());
                    break;
                }
                case 1: {
                    // Phoenix
                    ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
                    PotionMeta phoenixMeta = (PotionMeta) phoenix.getItemMeta();
                    phoenixMeta.setDisplayName(ChatColor.GOLD + "不死鳳凰");
                    phoenixMeta.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 90, 9), true);
                    phoenix.setItemMeta(phoenixMeta);
                    inventory.addItem(phoenix);
                    Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "不死鳳凰").getMessages());
                    break;
                }
                case 2:
                    // Teleporter
                    inventory.addItem(new ItemStack(Material.ENDER_PEARL, 6));
                    Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "傳送者").getMessages());
                    break;
                default:
                    return;
            }

            kitReceivers.add(getPlayer());
            e.getWhoClicked().closeInventory();
        }
    }

}
