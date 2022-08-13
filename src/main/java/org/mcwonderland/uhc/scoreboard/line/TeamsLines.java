package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class TeamsLines extends SoloLines {

    public TeamsLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer) {
        SimpleReplacer replace = super.replace(uhcPlayer, simpleReplacer);
        UHCTeam team = uhcPlayer.getTeam();

        if (team == null)
            return replace;

        return replace
                .replace("{team_name}", team.getName())
                .replace("{team_color}", team.getColor())
                .replace("{team_kills}", team.getKills());

    }
}
