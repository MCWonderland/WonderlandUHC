package org.mcwonderland.uhc.game.player.role.models;

import org.bukkit.entity.Player;

public abstract class RoleChat {

    public abstract void chat(Player player, String message);

    protected String replace(String model, Player player, String message) {
        return model
                .replace("{player}", player.getName())
                .replace("{msg}", message);
    }

}
