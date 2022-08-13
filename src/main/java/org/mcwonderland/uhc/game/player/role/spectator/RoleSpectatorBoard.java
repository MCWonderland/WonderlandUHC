package org.mcwonderland.uhc.game.player.role.spectator;

import org.mcwonderland.uhc.game.player.role.models.SimpleRoleBoard;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;

public class RoleSpectatorBoard extends SimpleRoleBoard {

    @Override
    protected ScoreLines getSoloLines(SidebarTheme theme) {
        return theme.getSpectatorSoloLines();
    }

    @Override
    protected ScoreLines getTeamLines(SidebarTheme theme) {
        return theme.getSpectatorTeamsLines();
    }
}
