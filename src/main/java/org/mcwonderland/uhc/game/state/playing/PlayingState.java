package org.mcwonderland.uhc.game.state.playing;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.game.StateName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.state.SimpleGameState;
import org.mcwonderland.uhc.game.state.playing.listener.*;
import org.mcwonderland.uhc.game.state.playing.listener.*;
import org.mcwonderland.uhc.game.state.playing.listener.death.PlayingDeathListener;
import org.mcwonderland.uhc.game.timer.Timer;
import org.mcwonderland.uhc.game.timer.Timers;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;
import org.bukkit.event.Listener;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.remain.Remain;

import java.util.ArrayList;
import java.util.Collection;

public class PlayingState extends SimpleGameState {

    public PlayingState(StateName name) {
        super(name);
    }

    @Override
    protected ScoreLines getScoreLines(SidebarTheme theme, UHCPlayer uhcPlayer) {
        return uhcPlayer.getBoard().getGamingLines(theme);
    }

    @Override
    protected Collection<Listener> initListeners() {
        ArrayList<Listener> listeners = Lists.newArrayList(
                new BlockListener(),
                new CombatRelogListener(),
                new DamageListener(),
                new DisableItemListener(),
                new GoldenHeadListener(),
                new InteractListener(),
                new InventoryListener(),
                new IPvPListener(),
                new ItemListener(),
                new PearlDamageListener(),
                new PlayerStateListener(),
                new PlayingJoinListener(),
                new PlayingLoginListener(),
                new PlayingMotdListener(),
                new PlayingQuitListener(),
                new PlayingDeathListener(),
                new PortalListener(),
                new StatsListener()
        );

        if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_11) && Remain.isPaper())
            listeners.add(new PaperListener());

        return listeners;
    }

    @Override
    public Iterable<Timer> getTimers() {
        return Lists.newArrayList(
                Timers.DAMAGE,
                Timers.FINAL_HEAL,
                Timers.PVP,
                Timers.BORDER,
                Timers.RELOG_CHECKER,
                Timers.BORDER_SIZE_UPDATER,
                Timers.NETHER_CLOSE
        );
    }

}
