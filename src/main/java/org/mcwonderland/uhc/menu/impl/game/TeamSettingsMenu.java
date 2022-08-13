package org.mcwonderland.uhc.menu.impl.game;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.menu.model.ColorPickerMenu;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Chat;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigClickableButton;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.button.config.conversation.ConfigSaveInputButton;
import org.mineacademy.fo.menu.button.config.value.ConfigBooleanButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.model.SimpleReplacer;

public class TeamSettingsMenu extends ConfigMenu {

    private final ConfigMenuButton nameEditButton;
    private final ConfigMenuButton colorEditButton;
    private final ConfigMenuButton charEditButton;
    private final ConfigMenuButton openJoinButton;
    private final ConfigMenuButton helpButton;

    public TeamSettingsMenu(UHCTeam team) {
        super(UHCMenuSection.of("Team_Settings"), null);

        nameEditButton = new ConfigSaveInputButton(getButtonPath("Name")) {

            @Override
            protected void onSaveInput(String input) {
                team.setName(input);
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Text.TeamName.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Text.TeamName.SAVED
                        .replace("{player}", getPlayer().getName())
                        .replace("{name}", team.getName());
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{name}", team.getName());
            }

            @Override
            protected String getPermission() {
                return UHCPermission.TEAM_SETTINGS_NAME.toString();
            }
        };

        charEditButton = new ConfigSaveInputButton(getButtonPath("Character")) {
            @Override
            protected void onSaveInput(String input) {
                team.setSymbol(getCharacterCut(input));
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Text.TeamCharacter.MESSAGE
                        .replace("{length}", Settings.Team.MAX_CHARACTER_LENGTH + "");
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Text.TeamCharacter.SAVED
                        .replace("{player}", getPlayer().getName())
                        .replace("{character}", team.getSymbol());
            }

            private String getCharacterCut(String fullChar) {
                return StringUtils.left(fullChar, Settings.Team.MAX_CHARACTER_LENGTH);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{character}", team.getSymbol());
            }

            @Override
            protected boolean isInputValid(String input) {
                boolean used = UHCTeam.getTeams().stream().map(UHCTeam::getSymbol)
                        .anyMatch(s -> s.equalsIgnoreCase(input));

                if (used)
                    Chat.sendConversing(getPlayer(), Messages.Editor.Text.TeamCharacter.ALREADY_USED
                            .replace("{symbol}", input));

                return !used;
            }

            @Override
            protected String getPermission() {
                return UHCPermission.TEAM_SETTINGS_CHARACTER.toString();
            }
        };

        colorEditButton = new ConfigClickableButton(getButtonPath("Color")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                new ColorPickerMenu(menu) {
                    @Override
                    protected void onChooseColor(Player player, ChatColor chatColor) {
                        team.setColor(chatColor);
                    }
                }.displayTo(player);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{color}", team.getColor());
            }

            @Override
            protected String getPermission() {
                return UHCPermission.TEAM_SETTINGS_COLOR.toString();
            }
        };

        openJoinButton = new ConfigBooleanButton(getButtonPath("Open_Join")) {
            @Override
            protected void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
                player.performCommand("team public");
            }

            @Override
            protected boolean getBoolean() {
                return team.isOpenJoin();
            }
        };

        helpButton = new ConfigClickableButton(getButtonPath("Help")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                player.closeInventory();

                player.performCommand("team ?");
            }
        };
    }
}
