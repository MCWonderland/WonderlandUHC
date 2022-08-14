package org.mcwonderland.uhc.tools.spectator;

import lombok.Getter;
import org.mcwonderland.uhc.tools.UHCTool;
import org.bukkit.event.player.PlayerInteractEvent;

public final class ToggleGameModeItem extends UHCTool {

    @Getter
    private static final ToggleGameModeItem instance = new ToggleGameModeItem();

    private ToggleGameModeItem() {
        super("Spectator.Toggle_Gamemode");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        event.getPlayer().performCommand("spectoggle");
    }
}
