package org.mcwonderland.uhc.tools.staff;

import lombok.Getter;
import org.mcwonderland.uhc.menu.MainGui;
import org.mcwonderland.uhc.tools.UHCTool;
import org.bukkit.event.player.PlayerInteractEvent;

public class StaffOptionsItem extends UHCTool {

    @Getter
    private static final StaffOptionsItem instance = new StaffOptionsItem();

    private StaffOptionsItem() {
        super("Staff_Addon.Staff_Options");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        MainGui.abrirStaffOptions(event.getPlayer());
    }
}
