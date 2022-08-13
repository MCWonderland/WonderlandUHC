package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class WorldUtils {

    public void dropItems(Location l, Collection<ItemStack> items) {
        dropItems(l, items.toArray(new ItemStack[0]));
    }

    public void dropItems(Location l, ItemStack... items) {
        for (ItemStack i : items) {
            if (CompMaterial.isAir(i.getType()))
                return;

            l.getWorld().dropItemNaturally(centerOfBlock(l), i);
        }
    }

    public Location centerOfBlock(Location location) {
        Location newLocation = location.clone();

        if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_13)) {
            return newLocation;
        }

        return newLocation.add(0.5, 0.5, 0.5);
    }

    public void replaceDrop(List<ItemStack> drops, CompMaterial from, CompMaterial to) {
        replaceDrop(drops, from.getMaterial(), to.getMaterial());
    }

    public void replaceDrop(List<ItemStack> drops, Material from, Material to) {
        for (int i = 0; i < drops.size(); i++) {
            ItemStack original = drops.get(i);
            if (original.getType() == from) {
                ItemStack newOne = original.clone();
                newOne.setType(to);
                drops.set(i, newOne);
            }
        }
    }

    public int getDropsAmount(List<ItemStack> drops, CompMaterial item) {
        return getDropsAmount(drops, item.getMaterial());
    }

    public int getDropsAmount(List<ItemStack> drops, Material item) {
        return drops.stream().filter(itemStack -> itemStack.getType() == item)
                .mapToInt(itemStack -> itemStack.getAmount())
                .sum();
    }

    public void spawnOrb(Location l, int value) {
        spawnOrb(l, 1, value);
    }

    public void spawnOrb(Location l, int amount, int value) {
        if (value == 0)
            return;

        if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_13))
            DaTouNMS.getWorldNMS().spawnOrb(l, amount, value);
        else
            DaTouNMS.getWorldNMS().spawnOrb(l.add(0.5, 0.5, 0.5), amount, value);
    }

    public int getBlockEXP(Material blockType) {
        if (blockType.toString().contains("REDSTONE_ORE"))
            return Extra.randomizar(1, 5);

        CompMaterial oreType = CompMaterial.fromMaterial(blockType);

        if (CompMaterial.COAL_ORE == oreType)
            return Extra.randomizar(0, 2);
        if (CompMaterial.DIAMOND_ORE == oreType || CompMaterial.EMERALD_ORE == oreType)
            return Extra.randomizar(3, 7);
        if (CompMaterial.LAPIS_ORE == oreType || CompMaterial.NETHER_QUARTZ_ORE == oreType)
            return Extra.randomizar(2, 5);
        if (CompMaterial.IRON_ORE == oreType || CompMaterial.GOLD_ORE == oreType)
            return 1;

        return 0;
    }

    public boolean isOre(Material m) {
        return m.toString().contains("ORE");
    }

    public boolean isUHCWorld(World world) {
        return world.getName().contains(Settings.Game.UHC_WORLD_NAME);
    }
}
