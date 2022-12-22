package org.mcwonderland.uhc.game.settings.sub;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.model.InventoryContent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.List;

@Getter
@Setter
public class UHCItemSettings implements ConfigSerializable {
    private InventoryContent customInventoryItems;
    private InventoryContent practiceInventoryItems;
    private List<ItemStack> customDrops;
    private List<ItemStack> customDisabledItems;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Custom_Disabled_Items", customDisabledItems);
        map.put("Practice_Inventory_Items", practiceInventoryItems.serialize().asMap());
        map.put("Custom_Inventory_Items", customInventoryItems.serialize().asMap());
        map.put("Custom_Drops", customDrops);

        return map;
    }

    public static UHCItemSettings deserialize(SerializedMap map) {
        UHCItemSettings uhcItemSettings = new UHCItemSettings();

        uhcItemSettings.customDisabledItems = map.getList("Custom_Disabled_Items", ItemStack.class);
        uhcItemSettings.customDrops = map.getList("Custom_Drops", ItemStack.class);
        uhcItemSettings.customInventoryItems = map.get("Custom_Inventory_Items", InventoryContent.class, InventoryContent.empty());
        uhcItemSettings.practiceInventoryItems = map.get("Practice_Inventory_Items", InventoryContent.class, InventoryContent.empty());

        return uhcItemSettings;
    }

    private static ItemStack[] getItemArray(SerializedMap map, String path) {
        return map.getList(path, ItemStack.class).toArray(new ItemStack[0]);
    }
}
