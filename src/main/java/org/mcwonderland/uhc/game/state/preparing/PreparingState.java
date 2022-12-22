package org.mcwonderland.uhc.game.state.preparing;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.game.StateName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.state.SimpleGameState;
import org.mcwonderland.uhc.game.timer.Timer;
import org.mcwonderland.uhc.game.timer.Timers;
import org.mcwonderland.uhc.practice.Practice;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collection;

public class PreparingState extends SimpleGameState {

    public PreparingState(StateName name) {
        super(name);
    }

    @Override
    protected Collection<Listener> initListeners() {
        return Lists.newArrayList(
                new PreparingCommonListener(),
                new PreparingJoinListener(),
                new PreparingLoginListener(),
                new PreparingQuitListener(),
                new PreparingMotdListener()
        );
    }

    @Override
    protected void onEnd() {
        Practice practice = WonderlandUHC.getInstance().getPractice();

        for (Player player : practice.getPlayers())
            practice.quit(player);
    }

    @Override
    public Iterable<Timer> getTimers() {
        return Lists.newArrayList(
                Timers.LOBBY
        );
    }

    @Override
    protected ScoreLines getScoreLines(SidebarTheme theme, UHCPlayer uhcPlayer) {
        return theme.getLobbyLines();
    }
}
