package org.mcwonderland.uhc.game.player.role.models;

import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;
import org.mcwonderland.uhc.util.GameUtils;

public abstract class SimpleRoleBoard implements RoleBoard {

    @Override
    public final ScoreLines getGamingLines(SidebarTheme theme) {
        if (GameUtils.isTeamMode())
            return getTeamLines(theme);
        return getSoloLines(theme);
    }

    protected abstract ScoreLines getSoloLines(SidebarTheme theme);

    protected abstract ScoreLines getTeamLines(SidebarTheme theme);
}
