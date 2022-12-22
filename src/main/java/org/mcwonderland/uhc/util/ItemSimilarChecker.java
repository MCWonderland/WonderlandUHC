package org.mcwonderland.uhc.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.mineacademy.fo.ItemUtil;

public class ItemSimilarChecker {
    private static ItemStack item;
    private static ItemStack comparision;
    private static ItemMeta itemMeta;
    private static ItemMeta comparisionMeta;

    public static boolean isSimilar(ItemStack item, ItemStack comparision) {
        if (item == null || comparision == null)
            return false;

        ItemSimilarChecker.item = item;
        ItemSimilarChecker.comparision = comparision;
        ItemSimilarChecker.itemMeta = item.getItemMeta();
        ItemSimilarChecker.comparisionMeta = comparision.getItemMeta();

        return checkSimilar();
    }

    private static boolean checkSimilar() {
        if (ItemUtil.isSimilar(item, comparision))
            return checkOther();

        return false;
    }

    private static boolean checkOther() {

        if (itemMeta instanceof PotionMeta && comparisionMeta instanceof PotionMeta)
            return checkPotion();

        return true;
    }

    private static boolean checkPotion() {
        try {
            PotionData potionMeta = ((PotionMeta) itemMeta).getBasePotionData();
            PotionData comparisionData = ((PotionMeta) comparisionMeta).getBasePotionData();

            return potionMeta.equals(comparisionData);
        } catch (NoSuchMethodError | NoClassDefFoundError ex) {
            return Potion.fromItemStack(item).equals(Potion.fromItemStack(comparision));
        }
    }
}