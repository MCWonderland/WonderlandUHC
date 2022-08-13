package org.mcwonderland.uhc.tools.lobby;

import lombok.Getter;
import org.mcwonderland.uhc.tools.UHCTool;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamSettingsItem extends UHCTool {

    @Getter
    private static final TeamSettingsItem instance = new TeamSettingsItem();

    private TeamSettingsItem() {
        super("Lobby.Team_Settings");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        event.getPlayer().performCommand("team settings");
    }
}
