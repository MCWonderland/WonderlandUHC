package org.mcwonderland.uhc.menu.impl.game;

import org.mcwonderland.uhc.api.Scenario;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.scenario.ScenarioManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;

/**
 * 2019-11-27 上午 10:19
 */
public class EnabledScenariosMenu extends ConfigMenuPagged<Scenario> {

    public EnabledScenariosMenu(ScenarioManager manager) {
        super(null, UHCMenuSection.of("Enabled_Scenarios"), manager.getEnabledScenarios());
    }

    @Override
    protected ItemStack convertToItemStack(Scenario scenario) {
        return scenario.getIcon();
    }

    @Override
    protected void onPageClick(Player player, Scenario scenario, ClickType clickType) {

    }
}
