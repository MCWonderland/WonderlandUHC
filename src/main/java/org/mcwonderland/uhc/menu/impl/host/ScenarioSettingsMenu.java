package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.api.Scenario;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.scenario.ScenarioManager;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;

/**
 * 2019-12-10 上午 01:00
 */
public class ScenarioSettingsMenu extends ConfigMenuPagged<Scenario> {
    private final Button clearScenariosButton;

    public ScenarioSettingsMenu(Menu parent, ScenarioManager manager) {
        super(parent, UHCMenuSection.of("Scenarios"), manager.getScenarios());

        clearScenariosButton = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                manager.getEnabledScenarios().forEach(scenario -> scenario.toggleEnabled(false));
                Game.getSettings().getScenarios().clear();
                Extra.sound(player, Sounds.Host.CLEAR_ENABLED_SCENARIOS);
                restartMenu();
            }

            @Override
            public ItemStack getItem() {
                return section.getButtonItem("Clear_Scenarios");
            }
        };
    }

    @Override
    protected ItemStack convertToItemStack(Scenario scenario) {
        return ItemCreator.of(scenario.getIcon())
                .lore("")
                .lore(scenario.isEnabled() ? Messages.ENABLED : Messages.DISABLED)
                .glow(scenario.isEnabled())
                .hideTags(true)
                .make();
    }

    @Override
    protected void onPageClick(Player player, Scenario scenario, ClickType clickType) {
        scenario.toggleEnabled(!scenario.isEnabled());

        Chat.broadcast((scenario.isEnabled() ?
                Messages.Host.SCENARIO_ENABLED_PLAYER : Messages.Host.SCENARIO_DISABLED_PLAYER)
                .replace("{player}", player.getName())
                .replace("{scenario}", scenario.getFancyName()));

        Extra.sound(Sounds.Host.SCENARIO_TOGGLED);
    }

    @Override
    public ItemStack getItemAt(int slot) {
        if (slot == getSize() - 9)
            return clearScenariosButton.getItem();

        return super.getItemAt(slot);
    }

    @Override
    protected String[] getInfo() {
        return null;
    }
}
