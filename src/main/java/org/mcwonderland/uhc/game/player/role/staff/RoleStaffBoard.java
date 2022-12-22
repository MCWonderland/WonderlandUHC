package org.mcwonderland.uhc.game.player.role.staff;

import org.mcwonderland.uhc.game.player.role.models.SimpleRoleBoard;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;

public class RoleStaffBoard extends SimpleRoleBoard {

    @Override
    protected ScoreLines getSoloLines(SidebarTheme theme) {
        return theme.getStaffSoloLines();
    }

    @Override
    protected ScoreLines getTeamLines(SidebarTheme theme) {
        return theme.getStaffTeamsLines();
    }
}
