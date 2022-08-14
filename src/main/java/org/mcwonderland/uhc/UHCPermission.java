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
    TEAM_SETTINGS_NAME,
    COMMAND_BACKPACK,
    COMMAND_BORDER,
    // new added
    COMMAND_GIVEALL,
    COMMAND_SET_SPAWN,
    COMMAND_RESPAWN,
    COMMAND_UHC_REGEN,
    COMMAND_UHC_START,
    // end
    COMMAND_CONFIG,
    COMMAND_DISABLEITEMS,
    COMMAND_LEAVE,
    COMMAND_MLG,
    COMMAND_PRACTICE,
    COMMAND_SCENARIOS,
    COMMAND_SENDCOORDS,
    COMMAND_SPECTOGGLE,
    COMMAND_STAFF,
    COMMAND_STATS,
    COMMAND_TEAM_CHAT,
    COMMAND_TEAM_CREATE,
    COMMAND_TEAM_DISBAND,
    COMMAND_TEAM_INVITE,
    COMMAND_TEAM_JOIN,
    COMMAND_TEAM_KICK,
    COMMAND_TEAM_LEAVE,
    COMMAND_TEAM_LIST,
    COMMAND_TEAM_PROMOTE,
    COMMAND_TEAM_PUBLIC,
    COMMAND_TEAM_SETTINGS,
    COMMAND_TOPKILLS,
    COMMAND_UHC_CHOOSE,
    COMMAND_UHC_EDIT,
    COMMAND_UHC_RELOAD,
    COMMAND_UHC_RESETTEAM,
    COMMAND_UHC_SETHOST,
    COMMAND_UHC_SPLITTEAM,
    COMMAND_UHC_STOP,
    COMMAND_UHC_SWITCHTEAM,
    COMMAND_UHC_TP,
    COMMAND_UHC_TUTORIAL,
    COMMAND_VIEWHEAL,
    COMMAND_WHITELIST;

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
