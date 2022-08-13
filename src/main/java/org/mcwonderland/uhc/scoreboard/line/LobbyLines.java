package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.timer.Timers;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class LobbyLines extends UHCLines {

    public LobbyLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer) {
        return super.replace(uhcPlayer, simpleReplacer);
    }

    @Override
    protected SimpleReplacer replaceGlobal(SimpleReplacer simpleReplacer) {
        UHCGameSettings settings = Game.getSettings();

        return super.replaceGlobal(simpleReplacer)
                .replace("{teleport_in}", Timers.getUntilEnableFormat(Timers.LOBBY))
                .replace("{team_size}", settings.getTeamSettings().getTeamSize())
                .replace("{max_players}", settings.getMaxPlayers());
    }
}
