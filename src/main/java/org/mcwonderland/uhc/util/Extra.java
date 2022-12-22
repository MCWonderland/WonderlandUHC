package org.mcwonderland.uhc.util;

import me.lulu.datounms.model.RecipeHelper;
import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mineacademy.fo.BungeeUtil;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.remain.CompAttribute;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompProperty;
import org.mineacademy.fo.remain.Remain;

import java.io.File;
import java.util.*;

public class Extra {
    private static final Random r = new Random();

    public static ItemStack[] mergeArrays(ItemStack[]... arrays) {
        List<ItemStack> list = new ArrayList<>();

        for (ItemStack[] array : arrays)
            list.addAll(Arrays.asList(array));

        return list.toArray(new ItemStack[0]);
    }

    public static void deleteWorld(final String world) {
        final File filePath = new File(Bukkit.getWorldContainer(), world);
        deleteFiles(filePath);
    }

    private static boolean deleteFiles(final File path) {
        if (path.exists()) {
            final File[] files = path.listFiles();
            File[] arrayOfFile1;
            final int j = (Objects.requireNonNull(arrayOfFile1 = files)).length;
            for (int i = 0; i < j; i++) {
                final File file = arrayOfFile1[i];
                if (file.isDirectory()) {
                    deleteFiles(file);
                } else {
                    file.delete();
                }
            }
        }
        return path.delete();
    }

    public static void createHead() {
        ItemStack goldenHead = CompMaterial.GOLDEN_APPLE.toItem();
        ItemMeta gMeta = goldenHead.getItemMeta();
        gMeta.setDisplayName(Settings.Misc.GOLDEN_HEAD_NAME);
        goldenHead.setItemMeta(gMeta);

        ShapedRecipe goldenHeadRecipe = RecipeHelper.getNewShapedRecipe(goldenHead);
        goldenHeadRecipe.shape("@@@", "@#@", "@@@");

        goldenHeadRecipe.setIngredient('@', CompMaterial.GOLD_INGOT.getMaterial());
        goldenHeadRecipe.setIngredient('#', CompMaterial.PLAYER_HEAD.getMaterial());
        Bukkit.getServer().addRecipe(goldenHeadRecipe);
    }

    public static void sound(Player player, SimpleSound sound) {
        sound.play(player);
    }

    public static void sound(Collection<Player> players, SimpleSound sound) {
        sound.play(players);
    }

    public static void sound(Location location, SimpleSound sound) {
        sound.play(location);
    }

    public static void sound(SimpleSound sound) {
        sound.play(( Collection ) Remain.getOnlinePlayers());
    }

    public static void potion(Player p, PotionEffectType type, int duration, int amplifier, boolean displayEffect) {
        p.addPotionEffect(new PotionEffect(type, duration, amplifier), !displayEffect);
    }

    public static void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public static void comepleteClear(Player player) {
        CompAttribute.GENERIC_MAX_HEALTH.set(player, 20);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.setLevel(0);
        player.setExp(0);
        player.getActivePotionEffects().forEach(ps -> player.removePotionEffect(ps.getType()));
        player.setFireTicks(0);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
    }

    public static void playBlockBreakEffect(Location location, Material m) {
        location.getWorld().playEffect(location, Effect.STEP_SOUND, m);
    }

    public static Integer getOnlinePlayers() {
        return Remain.getOnlinePlayers().size();
    }

    public static void sendToFallbackServer(Player p) {
        BungeeUtil.connect(p, Settings.BUNGEE_LOBBY);
    }

    public static void copyHealth(LivingEntity entity, LivingEntity copyTo) {
        setMaxHealth(copyTo, getMaxHealth(entity));
        copyTo.setHealth(entity.getHealth());
    }

    public static void setMaxHealth(LivingEntity entity, double health) {
        CompAttribute attribute = CompAttribute.GENERIC_MAX_HEALTH;
        health = Math.max(0, health);

        attribute.set(entity, health);
        entity.setHealth(health);
    }

    public static double getMaxHealth(LivingEntity entity) {
        return CompAttribute.GENERIC_MAX_HEALTH.get(entity);
    }

    public static void noAIAndSilent(LivingEntity entity) {
        CompProperty.AI.apply(entity, false);
        CompProperty.SILENT.apply(entity, true);
    }

    public static double formatHealth(double health) {
        return toTwoDecimals(health);
    }


    public static double toTwoDecimals(double d) {
        return ( int ) Math.round(d * 100) / 100.0;
    }

    public static Integer randomizar(int min, int max) {
        return r.nextInt((max - min) + 1) + min;
    }

    public static Integer randomNum(int max) {
        return r.nextInt(max);
    }

    public static boolean isBetween(int var, int min, int max) {
        return var >= min && var <= max;
    }

    public static boolean isDamageable(Material m) {
        return m.getMaxDurability() > 1;
    }

    public static Location getHighestSafeLocation(World w, int x, int z) {
        for (int run = 0; run < 20; run++) {
            final int y = getHighestPoint(w, x, z);
            final Block block = w.getBlockAt(x, y, z);
            final Block above = block.getRelative(BlockFace.UP);
            final Block down = block.getRelative(BlockFace.DOWN);
            if (y != -1 && CompMaterial.isAir(block) && CompMaterial.isAir(above)
                    && down.getType().isSolid())
                return new Location(w, x, y, z);
        }
        return null;
    }

    public static final int getHighestPoint(World w, int x, int z) {
        for (int y = w.getMaxHeight(); y >= 0; y--) {
            if (!CompMaterial.isAir(w.getBlockAt(x, y, z)))
                return y + 1;
        }
        return -1;
    }

    public static void restartServer() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Settings.RESTART_CMD);
    }
}
