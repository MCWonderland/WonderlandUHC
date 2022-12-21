package org.mcwonderland.uhc.scenario.impl.special;

import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageByEntityEvent;
import org.mcwonderland.uhc.events.UHCGameTimerUpdateEvent;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;

import java.util.*;

import static org.bukkit.Material.STONE_SWORD;

public class ScenarioMole extends ConfigBasedScenario implements Listener {

    //todo
    // mole kit

    @FilePath(name = "Mole_Spawn_Minutes")
    private Integer moleSpawnMinutes;

    @FilePath(name = "Sword_Given_After_Seconds")
    private Integer swordGivenAfterSeconds;

    @FilePath(name = "Mole_Countdown_Sound")
    private SimpleSound moleSpawnCountdownSound;

    @FilePath(name = "Mole_Spawn_Sound")
    private SimpleSound moleSpawnSound;

    @FilePath(name = "Sword_Given_Sound")
    private SimpleSound swordGivenSound;

    @FilePath(name = "Mole_Team_Name")
    private static String moleTeamName;

    @FilePath(name = "Mole_Access_Deny_Message")
    private static String moleAccessDeniedMessage;

    @FilePath(name = "Mole_Countdown_Message")
    private static String moleCountdownMessage;

    @FilePath(name = "Sword_Receiver_Message")
    private static String swordReceiverMessage;

    @FilePath(name = "Mole_Exposed_Message")
    private static String moleExposedMessage;

    @FilePath(name = "Sword_Misdamage_Message")
    private static String swordMisdamageMessage;

    @FilePath(name = "Mole_Player_Message")
    private List<String> molePlayerMessage;

    @FilePath(name = "NotMole_Player_Message")
    private List<String> notMolePlayerMessage;

    @FilePath(name = "Sword_Lore")
    private List<String> swordLore;

    private Integer moleSpawnSeconds;

    private static final Set<UHCPlayer> molePlayers = new HashSet<>();

    private static final Set<String> moleNames = new HashSet<>();

    public ScenarioMole(ScenarioName name) {
        super(name);
    }

    @Override
    protected void onConfigReload() {
        moleSpawnSeconds = moleSpawnMinutes * 60 + 6;
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replaceTime(moleSpawnMinutes * 60);
    }

    @EventHandler
    public void setMoleSpawnCountdown(UHCGameTimerUpdateEvent event) {
        int currentSecond = event.getCurrentSecond();
        int secondToSpawn = moleSpawnSeconds - currentSecond;
        int secondToSword = swordGivenAfterSeconds + secondToSpawn;

        if (secondToSpawn == 0) {
            doMoleSpawn();
            sendMoleSpawnMessage();
            Extra.sound(moleSpawnSound);
        } else if (Extra.isBetween(secondToSpawn, 1, 5)) {
            Extra.sound(moleSpawnCountdownSound);
            Chat.broadcast(moleCountdownMessage.replace("{second}", "" + secondToSpawn));
        }

        if (secondToSword == 0) {
            Extra.sound(swordGivenSound);
            swordGive();
        }
    }

    public void doMoleSpawn() {
        for (UHCTeam team : UHCTeam.getAliveTeams()) {
            Object[] teamPlayers = team.getAlivePlayers().toArray();

            Random rndm = new Random();
            int rndmNumber = rndm.nextInt(team.getPlayersAmount());

            molePlayers.add(UHCPlayer.getUHCPlayer((Player) teamPlayers[rndmNumber]));
        }
    }

    public void sendMoleSpawnMessage() {
        for (UHCPlayer player : UHCPlayer.getAllPlayers()) {
            if (molePlayers.contains(player)) {
                Chat.send(player.getPlayer(), molePlayerMessage);
            } else if (player.isAlive()) {
                Chat.send(player.getPlayer(), notMolePlayerMessage);
            }
        }
    }

    public static Set<UHCPlayer> getMoleList() {
        return Sets.newHashSet(molePlayers);
    }

    public static Set<String> getMoleNames() {
        for (UHCPlayer uhcPlayer : molePlayers) {
            moleNames.add(uhcPlayer.getName());
        }
        return Sets.newHashSet(moleNames);
    }

    public static String getMoleTeamName() {
        return moleTeamName;
    }

    public static String getMoleCommandDeniedMessage() {
        return moleAccessDeniedMessage;
    }

    private void swordGive() {
        ItemStack sword = new ItemStack(STONE_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();

        swordMeta.setDisplayName(ChatColor.RED + "懲戒之劍");
        swordMeta.setLore(swordLore);
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, false);

        sword.setItemMeta(swordMeta);

        for (UHCTeam team : UHCTeam.getAliveTeams()) {
            Object[] teamPlayers = team.getAlivePlayers().toArray();

            Random rndm = new Random();
            int rndmNumber = rndm.nextInt(team.getPlayersAmount());

            UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer((Player) teamPlayers[rndmNumber]);
            Player player = uhcPlayer.getPlayer();
            player.getInventory().addItem(sword);
            Chat.send(player, swordReceiverMessage);

            if (getMoleList().contains(uhcPlayer))
                return;
        }
    }

    @EventHandler
    private void checkSwordDamage(UHCPlayerDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        UHCPlayer UHCDamageTaker = event.getUhcPlayer();

        if (damager instanceof Player) {
            Player damagerPlayer = (Player) damager;

            UHCPlayer UHCDamager = UHCPlayer.getUHCPlayer(damagerPlayer);

            Location location = UHCDamageTaker.getPlayer().getLocation();
            World world = location.getWorld();

            String version = Bukkit.getBukkitVersion().split("-")[0];

            ItemStack item = UHCDamager.getPlayer().getInventory().getItemInHand();
            ItemMeta itemMeta = item.getItemMeta();

            if (itemMeta != null) {
                // 這個判斷式真的不太好
                if (itemMeta.getDisplayName().equals(ChatColor.RED + "懲戒之劍")) {
                    if (getMoleList().contains(UHCDamageTaker)
                            && UHCDamageTaker.getRoleName() == RoleName.PLAYER) {
                        world.strikeLightning(location);
                        breakSword(version, item, itemMeta);
                        Chat.broadcast(moleExposedMessage.replace("{player}", UHCDamageTaker.getName()));
                    } else if (UHCDamageTaker.getRoleName() == RoleName.PLAYER) {
                        UHCDamager.getPlayer().damage(UHCDamager.getPlayer().getHealthScale());
                        breakSword(version, item, itemMeta);
                        Chat.send(UHCDamager.getPlayer(), swordMisdamageMessage);
                    }
                }
            }
        }

    }

    private void breakSword(String version, ItemStack item, ItemMeta itemMeta) {
        // 這樣寫也真的不太好，但目前沒想到有其他方法可以支援多版本
        switch (version) {
            case "1.8.8":
            case "1.9":
            case "1.9.2":
            case "1.9.4":
            case "1.10":
            case "1.10.2":
            case "1.11":
            case "1.11.2":
            case "1.12":
            case "1.12.1":
            case "1.12.2":
                item.setDurability((short) 0);
                break;
            case "1.13":
            case "1.13.1":
            case "1.13.2":
            case "1.14":
            case "1.14.1":
            case "1.14.2":
            case "1.14.3":
            case "1.14.4":
            case "1.15":
            case "1.15.1":
            case "1.15.2":
            case "1.16.1":
            case "1.16.2":
            case "1.16.3":
            case "1.16.4":
            case "1.16.5":
                Damageable itemMetaDamage = (Damageable) itemMeta;
                itemMetaDamage.setDamage((int) STONE_SWORD.getMaxDurability());
                break;
        }
        item.setItemMeta(itemMeta);

    }

    public static void chooseKit(Inventory kitSelector, Player player) {
        // Engineer
        ItemStack engineer = new ItemStack(Material.TNT);
        ItemMeta engMeta = engineer.getItemMeta();
        engMeta.setDisplayName(ChatColor.AQUA + "工程師");
        engMeta.setLore(Arrays.asList(ChatColor.WHITE + "活塞x20, 黏性活塞x20, 紅石火把x16, 陷阱箱x6, 漏斗x10, 紅石磚x10, 絆線鉤x10, TNTx10"));
        engineer.setItemMeta(engMeta);
        kitSelector.setItem(0, engineer);

        // Scout
        ItemStack scout = new ItemStack(Material.FEATHER);
        ItemMeta scoutMeta = scout.getItemMeta();
        scoutMeta.setDisplayName(ChatColor.AQUA + "偵查兵");
        scoutMeta.setLore(Arrays.asList(ChatColor.WHITE + "永久速度I"));
        scout.setItemMeta(scoutMeta);
        kitSelector.setItem(1, scout);

        // Phoenix
        ItemStack phoenix = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta phoenixMeta = phoenix.getItemMeta();
        phoenixMeta.setDisplayName(ChatColor.AQUA + "不死鳳凰");
        phoenixMeta.setLore(Arrays.asList(ChatColor.GOLD.toString() + ChatColor.BOLD + "特殊" + ChatColor.WHITE + "胡蘿蔔x1 " + ChatColor.GRAY + "(吃了會獲得 吸收9(60顆愛心) 90秒)"));
        phoenix.setItemMeta(phoenixMeta);
        kitSelector.setItem(2, phoenix);

        // Teleporter
        ItemStack teleport = new ItemStack(Material.ENDER_PEARL);
        ItemMeta teleportMeta = teleport.getItemMeta();
        teleportMeta.setDisplayName(ChatColor.AQUA + "傳送者");
        teleportMeta.setLore(Arrays.asList(ChatColor.WHITE + "終界珍珠x16"));
        teleport.setItemMeta(teleportMeta);
        kitSelector.setItem(3, teleport);


        player.openInventory(kitSelector);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Chat.broadcast("asdasdasda");
//        if (e.getClickedInventory() == kitSelector && e.getCurrentItem() != null) {
//            e.setCancelled(true);
//
//            switch (e.getSlot()) {
//                case 0:
//                    giveKitEngineer(player);
//                    break;
//                case 1:
//                    giveKitScout(player);
//                    break;
//                case 2:
//                    giveKitPhoenix(player);
//                    break;
//                case 3:
//                    giveKitTeleporter(player);
//                    break;
//                default:
//                    return;
//            }
//
//            e.getWhoClicked().closeInventory();
//        }
    }

    public void giveKitEngineer(Player player) {
        Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "工程師").getMessages());
    }

    public void giveKitScout(Player player) {
        Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "偵查兵").getMessages());
    }

    public void giveKitPhoenix(Player player) {
        Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "不死鳳凰").getMessages());
    }

    public void giveKitTeleporter(Player player) {
        Chat.send(player, new SimpleReplacer(CommandSettings.Mole.KIT_RECEIVED).replace("{kit}", "傳送者").getMessages());
    }


}



