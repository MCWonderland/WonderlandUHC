package org.mcwonderland.uhc.model;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.Arrays;
import java.util.List;

public class InventoryContent implements ConfigSerializable {
    private List<ItemStack> storage;
    private List<ItemStack> armor;
    private List<ItemStack> extra;

    public InventoryContent(Player player) {
        this(player.getInventory());
    }

    public InventoryContent(PlayerInventory inv) {
        this.storage = getStorageContent(inv);
        this.extra = getExtraContent(inv);
        this.armor = Arrays.asList(inv.getArmorContents().clone());
    }

    private InventoryContent() {
        this.storage = Lists.newArrayList();
        this.extra = Lists.newArrayList();
        this.armor = Lists.newArrayList();
    }

    public static InventoryContent empty() {
        return new InventoryContent();
    }

    public static ItemStack[] contentsOf(Player player) {
        return contentsOf(player.getInventory());
    }

    public static ItemStack[] contentsOf(PlayerInventory inventory) {
        return new InventoryContent(inventory).getAllItems();
    }

    private List<ItemStack> getStorageContent(PlayerInventory inv) {
        //getContents() returns all contents in version above 1_9_R2,
        // or it's gonna returns storage only.
        //This class is made to solve this problem.

        try {
            return Arrays.asList(inv.getStorageContents().clone());
        } catch (NoSuchMethodError e) {
            return Arrays.asList(inv.getContents());
        }
    }

    private List<ItemStack> getExtraContent(PlayerInventory inv) {
        try {
            return Arrays.asList(inv.getExtraContents().clone());
        } catch (NoSuchMethodError e) {
            return Arrays.asList(new ItemStack[0]);
        }
    }

    public void setContents(Player player) {
        setContents(player.getInventory());
    }

    public void setContents(PlayerInventory inv) {
        setStorageContents(inv);
        setExtraContents(inv);
        inv.setArmorContents(armor.toArray(new ItemStack[0]));
    }

    public ItemStack[] getAllItems() {
        return Extra.mergeArrays(storage.toArray(new ItemStack[0]), armor.toArray(new ItemStack[0]), extra.toArray(new ItemStack[0]));
    }

    private void setExtraContents(PlayerInventory inventory) {
        try {
            inventory.setExtraContents(extra.toArray(new ItemStack[0]));
        } catch (NoSuchMethodError e) {

        }
    }

    private void setStorageContents(PlayerInventory inventory) {
        try {
            inventory.setStorageContents(storage.toArray(new ItemStack[0]));
        } catch (NoSuchMethodError e) {
            inventory.setContents(storage.toArray(new ItemStack[0]));
        }
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Storage", storage);
        map.put("Armor", armor);
        map.put("Extra", extra);

        return map;
    }

    public static InventoryContent deserialize(SerializedMap map) {
        InventoryContent inventoryContent = new InventoryContent();

        inventoryContent.storage = map.getList("Storage", ItemStack.class);
        inventoryContent.armor = map.getList("Armor", ItemStack.class);
        inventoryContent.extra = map.getList("Extra", ItemStack.class);

        return inventoryContent;
    }
}
