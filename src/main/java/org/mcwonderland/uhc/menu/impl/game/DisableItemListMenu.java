package org.mcwonderland.uhc.menu.impl.game;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;

public class DisableItemListMenu extends ConfigMenuPagged<ItemStack> {


    public DisableItemListMenu() {
        super(null, UHCMenuSection.of("Disable_Item_List"),
                Game.getSettings().getItemSettings().getCustomDisabledItems());
    }

    @Override
    protected ItemStack convertToItemStack(ItemStack item) {
        return item;
    }

    @Override
    protected void onPageClick(Player player, ItemStack item, ClickType click) {

    }
}
