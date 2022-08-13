package org.mcwonderland.uhc.menu;

import org.mcwonderland.uhc.settings.UHCFiles;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.button.ButtonReturnBack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

public class ButtonLocalization extends YamlConfig {

    public static void load() {
        new ButtonLocalization();
    }

    private ButtonLocalization() {
        loadConfiguration(UHCFiles.MENUS);

        setupReturnBackButton();
        setupPageToggleButtons();
    }

    private void setupReturnBackButton() {
        ItemStack item = getItem("Leave");

        ButtonReturnBack.setMaterial(CompMaterial.fromMaterial(item.getType()));
        ButtonReturnBack.setTitle(item.getItemMeta().getDisplayName());
        ButtonReturnBack.setLore(item.getItemMeta().getLore());
    }

    private void setupPageToggleButtons() {
//        MenuPagged.setNextPageItemModel(getItem("Next_Page"));
//        MenuPagged.setFirstPageItemModel(getItem("First_Page"));
//        MenuPagged.setPrevPageItemModel(getItem("Previous_Page"));
//        MenuPagged.setLastPageItemModel(getItem("Last_Page"));
    }

    private ItemStack getItem(String path) {
        path = path + ".";

        return ItemCreator.of(
                getMaterial(path + "Type")
                , getString(path + "Name"),
                getStringList(path + "Lore")
        ).make();
    }
}
