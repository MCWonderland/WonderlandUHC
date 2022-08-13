package org.mcwonderland.uhc.menu;

import org.mcwonderland.uhc.menu.impl.game.TeamSelectorMenu;
import org.mcwonderland.uhc.menu.impl.game.staff.StaffOptionsMenu;
import org.mcwonderland.uhc.menu.impl.host.CenterCleanerMenu;
import org.mcwonderland.uhc.menu.impl.host.MainSettingsMenu;
import org.bukkit.entity.Player;

public class MainGui {
    public static void abrirMenu(Player p) {
        new MainSettingsMenu(null).displayTo(p);
    }

    public static void abrirTeamList(Player p) {
        new TeamSelectorMenu().displayTo(p);
    }

    public static void abrirCenterCleaner(Player p) {
        new CenterCleanerMenu(null).displayTo(p);
    }

    public static void abrirStaffOptions(Player p) {
        new StaffOptionsMenu(p).displayTo(p);
    }
}
