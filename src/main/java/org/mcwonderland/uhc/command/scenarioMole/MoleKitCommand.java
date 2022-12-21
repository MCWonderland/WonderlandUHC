package org.mcwonderland.uhc.command.scenarioMole;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.ScenarioMole;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class MoleKitCommand extends SimpleSubCommand implements Listener {
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

        ScenarioMole.chooseKit(kitSelector, getPlayer());
    }

    public final Inventory kitSelector = Bukkit.createInventory(getPlayer(), 9, ChatColor.GOLD + "選擇一個職業");

//    public final Inventory KitSelector = Bukkit.createInventory(getPlayer(), 9, ChatColor.GOLD + "選擇一個職業");
//
//    private void chooseKit() {
//        // Engineer
//        ItemStack engineer = new ItemStack(Material.TNT);
//        ItemMeta engMeta = engineer.getItemMeta();
//        engMeta.setDisplayName(ChatColor.AQUA + "工程師");
//        engMeta.setLore(Arrays.asList(ChatColor.WHITE + "活塞x20, 黏性活塞x20, 紅石火把x16, 陷阱箱x6, 漏斗x10, 紅石磚x10, 絆線鉤x10, TNTx10"));
//        engineer.setItemMeta(engMeta);
//        KitSelector.setItem(0, engineer);
//
//        // Scout
//        ItemStack scout = new ItemStack(Material.FEATHER);
//        ItemMeta scoutMeta = scout.getItemMeta();
//        scoutMeta.setDisplayName(ChatColor.AQUA + "偵查兵");
//        scoutMeta.setLore(Arrays.asList(ChatColor.WHITE + "永久速度I"));
//        scout.setItemMeta(scoutMeta);
//        KitSelector.setItem(1, scout);
//
//        // Phoenix
//        ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
//        ItemMeta phoenixMeta = phoenix.getItemMeta();
//        phoenixMeta.setDisplayName(ChatColor.AQUA + "不死鳳凰");
//        phoenixMeta.setLore(Arrays.asList(ChatColor.GOLD.toString() + ChatColor.BOLD + "特殊" + ChatColor.WHITE + "胡蘿蔔x1 " + ChatColor.GRAY + "(吃了會獲得 吸收9(60顆愛心) 90秒)"));
//        phoenix.setItemMeta(phoenixMeta);
//        KitSelector.setItem(2, phoenix);
//
//        // Teleporter
//        ItemStack teleport = new ItemStack(Material.ENDER_PEARL);
//        ItemMeta teleportMeta = teleport.getItemMeta();
//        teleportMeta.setDisplayName(ChatColor.AQUA + "傳送者");
//        teleportMeta.setLore(Arrays.asList(ChatColor.WHITE + "終界珍珠x16"));
//        teleport.setItemMeta(teleportMeta);
//        KitSelector.setItem(3, teleport);
//
//
//        getPlayer().openInventory(KitSelector);
//    }
//
//    //todo
//    // kitSelector 可重複開啟
//    // onClick 沒有反應
//
//    @EventHandler
//    private void onClick(InventoryClickEvent e) {
//        if (e.getClickedInventory() == KitSelector && e.getCurrentItem() != null) {
//            e.setCancelled(true);
//
//            switch (e.getSlot()) {
//                case 0:
//                    giveKitEngineer();
//                    break;
//                case 1:
//                    giveKitScout();
//                    break;
//                case 2:
//                    giveKitPhoenix();
//                    break;
//                case 3:
//                    giveKitTeleporter();
//                    break;
//                default:
//                    return;
//            }
//
//            e.getWhoClicked().closeInventory();
//        }
//    }
//
//    public void giveKitEngineer() {
//        Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "工程師").getMessages());
//    }
//
//    public void giveKitScout() {
//        Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "偵查兵").getMessages());
//    }
//
//    public void giveKitPhoenix() {
//        Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "不死鳳凰").getMessages());
//    }
//
//    public void giveKitTeleporter() {
//        Chat.send(getSender(), new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "傳送者").getMessages());
//    }
}
