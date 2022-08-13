package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.StateName;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.timer.Timers;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class StartingLines extends LobbyLines {

    public StartingLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer) {
        return super.replace(uhcPlayer, simpleReplacer);
    }

    @Override
    protected SimpleReplacer replaceGlobal(SimpleReplacer simpleReplacer) {
        int teleported = Timers.SCATTER.getTeleportedNumbers();
        int teleporting = UHCTeam.getTeams().size() - teleported;

        return super.replaceGlobal(simpleReplacer)
                .replace("{start_in}", Game.getGame().getCurrentStateName() == StateName.TELEPORTING ? "--:--" : Timers.getUntilEnableFormat(Timers.START))
                .replace("{teleporting}", teleporting)
                .replace("{teleported}", teleported);
    }
}
