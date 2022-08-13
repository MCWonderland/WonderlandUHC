package org.mcwonderland.uhc.tools.spectator;

import lombok.Getter;
import org.mcwonderland.uhc.tools.UHCTool;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeleportZeroZeroItem extends UHCTool {

    @Getter
    private static final TeleportZeroZeroItem instance = new TeleportZeroZeroItem();

    private TeleportZeroZeroItem() {
        super("Spectator.Teleport_Zerozero");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        player.teleport(UHCWorldUtils.getZeroZero());
    }
}
