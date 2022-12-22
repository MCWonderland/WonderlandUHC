package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameTimerRunnable;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.timer.Timers;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class GameLines extends UHCLines {

    public GameLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer) {
        return super.replace(uhcPlayer, simpleReplacer);
    }

    @Override
    protected SimpleReplacer replaceGlobal(SimpleReplacer simpleReplacer) {
        int allTeams = UHCTeam.getTeams().size();
        int aliveTeams = UHCTeam.getAliveTeams().size();

        return super.replaceGlobal(simpleReplacer)
                .replace("{remaining}", UHCPlayers.countOf(uhcPlayer -> !uhcPlayer.isDead()))
                .replace("{alive_teams}", aliveTeams)
                .replace("{all_teams}", allTeams)
                .replace("{all}", Game.getGame().getAllPlayers())
                .replace("{spectators}", UHCPlayers.countOf(UHCPlayer::isDead))
                .replace("{border_size}", Game.getGame().getCurrentBorder())
                .replace("{border_size/2}", Game.getGame().getCurrentBorder() / 2)
                .replace("{shrink_in}", TimeUtil.formatTime(Timers.getSecondsUntilEnable(Timers.BORDER)))
                .replace("{game_time}", TimeUtil.formatTime(GameTimerRunnable.totalSecond));
    }
}
