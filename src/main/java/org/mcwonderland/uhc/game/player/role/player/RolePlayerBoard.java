package org.mcwonderland.uhc.game.player.role.player;

import org.mcwonderland.uhc.game.player.role.models.SimpleRoleBoard;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;

public class RolePlayerBoard extends SimpleRoleBoard {

    @Override
    protected ScoreLines getSoloLines(SidebarTheme theme) {
        return theme.getPlayerSoloLines();
    }

    @Override
    protected ScoreLines getTeamLines(SidebarTheme theme) {
        return theme.getPlayerTeamsLines();
    }
}
