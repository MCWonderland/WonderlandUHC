package org.mcwonderland.uhc.util;

import lombok.Getter;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCItemSettings;
import org.mcwonderland.uhc.model.InventoryContent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * 2019-11-24 下午 01:43
 */
public class InventorySaver {

    private final Player player;
    private final SaveType saveType;

    private InventorySaver(Player player, SaveType saveType) {
        this.player = player;
        this.saveType = saveType;
    }

    public static void saveInventoryData(Player player, SaveType saveType) {
        new InventorySaver(player, saveType).save();
    }

    public static void setContents(Player player, SaveType saveType) {
        UHCItemSettings itemSettings = Game.getSettings().getItemSettings();
        PlayerInventory inventory = player.getInventory();

        switch (saveType) {
            case CUSTOM_DROPS:
                clearAndSetContent(player, itemSettings.getCustomDrops());
                break;
            case CUSTOM_INVENTORY:
                itemSettings.getCustomInventoryItems().setContents(inventory);
                break;
            case PRACTICE_INVENTORY:
                itemSettings.getPracticeInventoryItems().setContents(inventory);
                break;
            case DISABLE_ITEMS:
                clearAndSetContent(player, itemSettings.getCustomDisabledItems());
                break;
        }
    }

    private static void clearAndSetContent(Player player, List<ItemStack> itemStacks) {
        InventoryContent.empty().setContents(player);
        setContentSafe(player.getInventory(), itemStacks.toArray(new ItemStack[0]));
    }

    private static void setContentSafe(PlayerInventory inventory, ItemStack[] itemStacks) {
        try {
            inventory.setContents(itemStacks);
        } catch (IllegalArgumentException ex) {
            inventory.setContents(Arrays.copyOf(itemStacks, 36));
        } catch (Exception ex) {

        }
    }

    private static ItemStack[] clearEmptySlots(ItemStack[] itemStacks) {
        return Arrays.stream(itemStacks).filter(Objects::nonNull).toArray(ItemStack[]::new);
    }

    private void save() {
        saveInventory(player.getInventory());
    }

    private void saveInventory(PlayerInventory inventory) {
        UHCItemSettings itemSettings = Game.getSettings().getItemSettings();

        InventoryContent content = new InventoryContent(inventory);
        List<ItemStack> itemsList = Arrays.asList(clearEmptySlots(content.getAllItems()));

        switch (saveType) {
            case CUSTOM_DROPS:
                itemSettings.setCustomDrops(itemsList);
                break;
            case DISABLE_ITEMS:
                itemsList.forEach(itemStack -> itemStack.setAmount(1));
                itemSettings.setCustomDisabledItems(itemsList);
                break;
            case CUSTOM_INVENTORY:
                itemSettings.setCustomInventoryItems(content);
                break;
            case PRACTICE_INVENTORY:
                itemSettings.setPracticeInventoryItems(content);
                break;
        }
    }

    @Getter
    public enum SaveType {
        CUSTOM_DROPS(false),
        CUSTOM_INVENTORY(true),
        PRACTICE_INVENTORY(true),
        DISABLE_ITEMS(false);

        private final boolean containArmors;

        SaveType(boolean containArmors) {
            this.containArmors = containArmors;
        }

        @Override
        public String toString() {
            return name().replace("_", "").toLowerCase();
        }
    }
}

