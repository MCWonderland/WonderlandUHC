package org.mcwonderland.uhc.game.player.role.models;

import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;

public interface RoleBoard {

    ScoreLines getGamingLines(SidebarTheme theme);

}
