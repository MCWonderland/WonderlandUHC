package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCScoreboardSettings;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.menu.model.ColorPickerMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigClickableButton;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.button.config.value.ConfigChangeValueButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.CompChatColor;

public class ScoreboardSettingsMenu extends ConfigMenu {

    private final ConfigMenuButton themesButton;
    private final ConfigMenuButton updateTicksButton;
    private final ConfigMenuButton heartColorButton;

    public ScoreboardSettingsMenu(Menu parent) {
        super(UHCMenuSection.of("Scoreboard"), parent);

        UHCScoreboardSettings scoreboardSettings = Game.getSettings().getScoreboardSettings();

        themesButton = new ConfigClickableButton(getButtonPath("Themes")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                new SidebarThemeSettingsMenu(menu, player).displayTo(player);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{theme}", scoreboardSettings.getSidebarTheme().getName());
            }
        };

        updateTicksButton = new ConfigChangeValueButton(getButtonPath("Update_Ticks"), 1) {
            @Override
            protected void onChangingSize(Player player, Menu menu, int newCount) {
                scoreboardSettings.setScoreboardUpdateTick(newCount);
            }

            @Override
            protected int getOriginalSize() {
                return scoreboardSettings.getScoreboardUpdateTick();
            }

            @Override
            protected Integer getMinimum() {
                return 1;
            }
        };

        heartColorButton = new ConfigClickableButton(getButtonPath("Heart_Color")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                openChooseHeartColorMenu(player);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{color}", scoreboardSettings.getHeartColor());
            }

            private void openChooseHeartColorMenu(Player player) {
                new ColorPickerMenu(ScoreboardSettingsMenu.this) {
                    @Override
                    protected void onChooseColor(Player player, ChatColor chatColor) {
                        scoreboardSettings.setHeartColor(CompChatColor.getByChar(chatColor.getChar()));
                    }
                }.displayTo(player);
            }
        };
    }
}
