package org.mcwonderland.uhc.api;

import org.bukkit.inventory.ItemStack;

public interface Scenario {

    void reload();

    void enable();

    void disable();

    default void toggleEnabled(boolean b) {
        if (b)
            enable();
        else
            disable();
    }

    boolean isEnabled();

    ItemStack getIcon();

    String getName();

    String getFancyName();
}
