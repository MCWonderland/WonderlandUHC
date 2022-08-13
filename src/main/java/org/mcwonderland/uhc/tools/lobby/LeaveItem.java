package org.mcwonderland.uhc.tools.lobby;

import lombok.Getter;
import org.mcwonderland.uhc.tools.UHCTool;
import org.bukkit.event.player.PlayerInteractEvent;

public class LeaveItem extends UHCTool {

    @Getter
    private static final LeaveItem instance = new LeaveItem();

    private LeaveItem() {
        super("Lobby.Leave");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        event.getPlayer().performCommand("leave");
    }
}
