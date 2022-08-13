package org.mcwonderland.uhc.tools.spectator;

import lombok.Getter;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.menu.impl.PlayersMenu;
import org.mcwonderland.uhc.tools.UHCTool;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.event.player.PlayerInteractEvent;

public class OverworldPlayersItem extends UHCTool {

    @Getter
    private static final OverworldPlayersItem instance = new OverworldPlayersItem();

    private OverworldPlayersItem() {
        super("Spectator.Overworld_Players");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        PlayersMenu menu = PlayersMenu.gamingPlayersMenu(UHCWorldUtils.getWorld(), UHCMenuSection.of("Players_Overworld"));

        menu.displayTo(event.getPlayer());
    }
}
