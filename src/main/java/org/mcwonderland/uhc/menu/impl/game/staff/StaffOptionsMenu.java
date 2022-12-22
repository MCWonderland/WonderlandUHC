package org.mcwonderland.uhc.menu.impl.game.staff;

import org.mcwonderland.uhc.game.player.staff.OreAlert;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.button.config.value.ConfigBooleanButton;
import org.mineacademy.fo.menu.button.config.value.ConfigChangeValueButton;
import org.mineacademy.fo.menu.config.ItemPath;

public class StaffOptionsMenu extends StaffMenu {

    private final ConfigMenuButton goldAlertButton, diamondAlertButton;
    private final ConfigMenuButton toggleSpecShowButton, toggleStaffShowButton;
    private final ConfigMenuButton movingSpeedButton;

    public StaffOptionsMenu(Player player) {
        super(player, UHCMenuSection.of("Staff_Options"), null);

        goldAlertButton = new OreAlertButton(getButtonPath("Gold_Alert"), OreAlert.GOLD_ORE);
        diamondAlertButton = new OreAlertButton(getButtonPath("Diamond_Alert"), OreAlert.DIAMOND_ORE);

        toggleSpecShowButton = new ToggleShowButton(getButtonPath("Toggle_Spec_Show")) {
            @Override
            protected void onToggleHide(Player player, Menu menu, boolean newStatus) {
                staffOption.setShowSpectator(newStatus);
            }

            @Override
            protected boolean getBoolean() {
                return staffOption.isShowSpectator();
            }
        };

        toggleStaffShowButton = new ToggleShowButton(getButtonPath("Toggle_Staff_Show")) {
            @Override
            protected void onToggleHide(Player player, Menu menu, boolean newStatus) {
                staffOption.setShowStaff(newStatus);
            }

            @Override
            protected boolean getBoolean() {
                return staffOption.isShowStaff();
            }
        };

        movingSpeedButton = new ConfigChangeValueButton(getButtonPath("Moving_Speed"), 1) {

            @Override
            protected void onChangingSize(Player player, Menu menu, int newCount) {
                staffOption.setSpeed(newCount);
                player.setWalkSpeed(staffOption.getMCSpeed());
                player.setFlySpeed(staffOption.getMCSpeed());
            }

            @Override
            protected int getOriginalSize() {
                return staffOption.getSpeed();
            }

            @Override
            protected Integer getMinimum() {
                return 1;
            }

            @Override
            protected Integer getMaximum() {
                return 5;
            }
        };
    }

    private class OreAlertButton extends ConfigBooleanButton {
        private final OreAlert oreAlert;

        protected OreAlertButton(ItemPath itemPath, OreAlert oreAlert) {
            super(itemPath);

            this.oreAlert = oreAlert;
        }

        @Override
        protected final void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
            staffOption.toggleOreAlert(oreAlert);
        }

        @Override
        protected final boolean getBoolean() {
            return staffOption.hasOreAlert(oreAlert);
        }
    }

    private abstract class ToggleShowButton extends ConfigBooleanButton {

        protected ToggleShowButton(ItemPath itemPath) {
            super(itemPath);
        }

        @Override
        protected final void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
            onToggleHide(player, menu, newStatus);
            uhcPlayer.checkHide();
        }

        protected abstract void onToggleHide(Player player, Menu menu, boolean newStatus);
    }
}
