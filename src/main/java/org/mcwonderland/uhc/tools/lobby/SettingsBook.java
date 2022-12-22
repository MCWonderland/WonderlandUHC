package org.mcwonderland.uhc.tools.lobby;

import lombok.Getter;
import org.mcwonderland.uhc.tools.UHCTool;
import org.bukkit.event.player.PlayerInteractEvent;

public final class SettingsBook extends UHCTool {

    @Getter
    private static final SettingsBook instance = new SettingsBook();

    private SettingsBook() {
        super("Lobby.Options");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        event.getPlayer().performCommand("uhc edit");
    }
}
