package org.mcwonderland.uhc.game.state.starting;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.game.StateName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.state.SimpleGameState;
import org.mcwonderland.uhc.game.state.share.CommonListener;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;
import org.bukkit.event.Listener;

import java.util.Collection;

public abstract class StartingState extends SimpleGameState {

    public StartingState(StateName name) {
        super(name);
    }

    @Override
    protected Collection<Listener> initListeners() {
        return Lists.newArrayList(
                new CommonListener(),
                new StartingJoinListener(),
                new StartingLoginListener(),
                new StartingMotdListener(),
                new StartingQuitListener()
        );
    }

    @Override
    protected ScoreLines getScoreLines(SidebarTheme theme, UHCPlayer uhcPlayer) {
        return theme.getStartingLines();
    }
}
