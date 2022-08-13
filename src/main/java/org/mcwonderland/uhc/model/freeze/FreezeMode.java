package org.mcwonderland.uhc.model.freeze;

import org.bukkit.entity.Player;

public interface FreezeMode {
    void freeze(Player player);

    void unFreeze(Player player);
}
