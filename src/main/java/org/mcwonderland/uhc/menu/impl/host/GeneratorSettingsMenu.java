package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.populator.Populator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;

public class GeneratorSettingsMenu extends ConfigMenuPagged<Populator> {

    public GeneratorSettingsMenu(Menu parent) {
        super(parent, UHCMenuSection.of("Generator"), Populator.getPopulators());
    }

    @Override
    protected ItemStack convertToItemStack(Populator populator) {
        return ItemCreator.of(
                populator.getIcon(),
                populator.getFancyName(),
                populator.getDescription()).make();
    }

    @Override
    protected void onPageClick(Player player, Populator populator, ClickType click) {
        Game.getSettings().setGenerator(populator.getKeyName());
        getParent().displayTo(player);
    }
}
