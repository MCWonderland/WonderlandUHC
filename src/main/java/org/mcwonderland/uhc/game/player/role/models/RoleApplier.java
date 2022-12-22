package org.mcwonderland.uhc.game.player.role.models;

import org.bukkit.entity.Player;

public interface RoleApplier {

    void apply(Player player);

    default void end(Player player) {
    }

}
