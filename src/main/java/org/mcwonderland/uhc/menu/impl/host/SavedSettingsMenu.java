package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.settings.UHCGameSettingsSaver;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.model.GamePlaceholderReplacer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.ConfigItem;

import java.util.List;

public class SavedSettingsMenu extends ConfigMenuPagged<UHCGameSettings> {

    private final ConfigItem info;
    private final Button saveAsButton;

    protected SavedSettingsMenu(Menu parent, Player player) {
        super(parent, UHCMenuSection.of("Saves"), UHCGameSettingsSaver.getSavedSettings(player));

        info = section.getConfigItem("Saved");

        saveAsButton = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                UHCGameSettingsSaver.getSavedSettings(player).add(Game.getSettings().clone());
                UHCGameSettingsSaver.saveGameSettings(player);

                refreshMenu(player);
            }


            @Override
            public ItemStack getItem() {
                return section.getButtonItem("Save_As");
            }
        };
    }


    @Override
    protected ItemStack convertToItemStack(UHCGameSettings settings) {

        return ItemCreator.of(info.getMaterial())
                .name(info.getName().replace("{saved_game_title}", settings.getTitle()))
                .lore(GamePlaceholderReplacer.replace(info.getLore(), settings))
                .make();
    }

    @Override
    protected void onPageClick(Player player, UHCGameSettings settings, ClickType clickType) {
        switch (clickType) {
            case LEFT:
                loadSettings(settings);
                refreshAndOpenMainMenu(player);
                break;
            case MIDDLE:
                replaceSettings(player, settings);
                break;
            case RIGHT:
                deleteSavedSettings(player, settings);
                refreshMenu(player);
                break;
        }
    }

    private void replaceSettings(Player player, UHCGameSettings settings) {
        List<UHCGameSettings> savedSettings = UHCGameSettingsSaver.getSavedSettings(player);

        savedSettings.set(savedSettings.indexOf(settings), Game.getSettings());
    }

    @Override
    public ItemStack getItemAt(int slot) {
        if (slot == getSize() - 9)
            return saveAsButton.getItem();

        return super.getItemAt(slot);
    }

    @Override
    protected String[] getInfo() {
        return null;
    }

    private void refreshAndOpenMainMenu(Player player) {
        new MainSettingsMenu(null).displayTo(player);
    }


    private void loadSettings(UHCGameSettings settings) {
        Game.changeSettings(settings.clone());
    }

    private void deleteSavedSettings(Player player, UHCGameSettings settings) {
        UHCGameSettingsSaver.getSavedSettings(player).remove(settings);
        UHCGameSettingsSaver.saveGameSettings(player);
    }

    private void refreshMenu(Player player) {
        new SavedSettingsMenu(getParent(), player).displayTo(player);
    }
}
