package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.api.enums.TeamSplitMode;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCTeamSettings;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigClickableButton;
import org.mineacademy.fo.menu.button.config.ConfigLeftOrRightButton;
import org.mineacademy.fo.menu.button.config.value.ConfigBooleanButton;
import org.mineacademy.fo.menu.button.config.value.ConfigChangeValueButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.model.SimpleReplacer;

public class TeamModeSettingsMenu extends ConfigMenu {

    private final ConfigClickableButton sizeButton;
    private final ConfigClickableButton teamFireButton;
    private final ConfigClickableButton splitTypeButton;

    public TeamModeSettingsMenu(Menu parent) {
        super(UHCMenuSection.of("Teams"), parent);

        UHCTeamSettings teamSettings = Game.getSettings().getTeamSettings();

        sizeButton = new ConfigChangeValueButton(getButtonPath("Size"), 1) {

            @Override
            protected void onChangingSize(Player player, Menu menu, int newCount) {
                teamSettings.setTeamSize(newCount);
            }


            @Override
            protected int getOriginalSize() {
                return teamSettings.getTeamSize();
            }

            @Override
            protected Integer getMinimum() {
                return 1;
            }
        };

        teamFireButton = new ConfigBooleanButton(getButtonPath("Team_Fire")) {

            @Override
            protected void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
                teamSettings.setAllowTeamFire(newStatus);

                broadcast(newStatus ? Messages.Host.TEAM_FIRE_ENABLED_PLAYER : Messages.Host.TEAM_FIRE_DISABLED_PLAYER, player);
            }

            @Override
            protected boolean getBoolean() {
                return teamSettings.isAllowTeamFire();
            }
        };

        splitTypeButton = new ConfigLeftOrRightButton(getButtonPath("Team_Split_Mode")) {
            @Override
            protected void onLeftClick(Player player, Menu menu) {
                teamSettings.setTeamSplitMode(TeamSplitMode.CHOSEN);
            }

            @Override
            protected void onRightClick(Player player, Menu menu) {
                teamSettings.setTeamSplitMode(TeamSplitMode.RANDOM);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{type}", teamSettings.getTeamSplitMode().name());
            }
        };
    }
}
