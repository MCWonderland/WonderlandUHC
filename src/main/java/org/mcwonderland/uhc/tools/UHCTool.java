package org.mcwonderland.uhc.tools;

import org.mcwonderland.uhc.settings.UHCFiles;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.model.ConfigItem;

public abstract class UHCTool extends Tool {

    private final ConfigItem configItem;

    protected UHCTool(String path) {
        super(true);
        configItem = ConfigItem.fromItemsFile(UHCFiles.ITEMS, path);
        register(this);
    }

    public final void set(Player player) {
        give(player, configItem.getSlot());
    }

    @Override
    public ItemStack getItem() {
        return configItem.getItem();
    }

    @Override
    protected boolean ignoreCancelled() {
        return false;
    }

    @Override
    protected final void onBlockClick(PlayerInteractEvent event) {
        final Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)
            onRightClick(event);
    }

    protected abstract void onRightClick(PlayerInteractEvent event);
}
