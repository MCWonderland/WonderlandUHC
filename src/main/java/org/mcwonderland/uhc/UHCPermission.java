package org.mcwonderland.uhc;

import org.bukkit.entity.Player;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.Valid;

public enum UHCPermission {
    BYPASS_JOIN_CONFIGURING,
    BYPASS_JOIN_STARTED,
    BYPASS_JOIN_WHITELIST,
    BYPASS_KICK_DEATH,
    ITEM_SETTINGS,
    SEE_SPECTATOR_CHAT("seespectatorchat"),
    SPECTATOR_CHAT_GLOBAL,
    TEAM_SETTINGS_CHARACTER,
    TEAM_SETTINGS_COLOR,
    TEAM_SETTINGS_NAME;

    private final String permission;

    UHCPermission() {
        this.permission = ("wonderland.uhc." + name().replace("_", ".")).toLowerCase();
    }

    UHCPermission(String permission) {
        this.permission = ("wonderland.uhc." + permission).toLowerCase();
    }

    public boolean hasPerm(Player player) {
        return PlayerUtil.hasPerm(player, permission);
    }

    public boolean checkPerms(Player player) {
        return Valid.checkPermission(player, permission);
    }

    public String toString() {
        return permission;
    }
}
