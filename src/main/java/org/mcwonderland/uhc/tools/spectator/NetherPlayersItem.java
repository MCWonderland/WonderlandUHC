package org.mcwonderland.uhc.tools.spectator;

import lombok.Getter;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.menu.impl.PlayersMenu;
import org.mcwonderland.uhc.tools.UHCTool;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.event.player.PlayerInteractEvent;

public class NetherPlayersItem extends UHCTool {

    @Getter
    private static final NetherPlayersItem instance = new NetherPlayersItem();

    private NetherPlayersItem() {
        super("Spectator.Nether_Players");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        PlayersMenu menu = PlayersMenu.gamingPlayersMenu(UHCWorldUtils.getNether(), UHCMenuSection.of("Players_Nether"));

        menu.displayTo(event.getPlayer());
    }
}
