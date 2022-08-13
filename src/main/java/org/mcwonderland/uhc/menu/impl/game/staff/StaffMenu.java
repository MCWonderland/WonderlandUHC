package org.mcwonderland.uhc.menu.impl.game.staff;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.staff.StaffOptions;
import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.menu.config.MenuSection;

abstract class StaffMenu extends ConfigMenu {

    protected final UHCPlayer uhcPlayer;
    protected final StaffOptions staffOption;

    public StaffMenu(Player player, MenuSection section, Menu parent) {
        super(section, parent);

        this.uhcPlayer = UHCPlayer.getUHCPlayer(player);
        this.staffOption = uhcPlayer.getStaffOptions();
    }

}
