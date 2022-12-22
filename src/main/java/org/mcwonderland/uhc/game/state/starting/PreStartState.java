package org.mcwonderland.uhc.game.state.starting;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.game.StateName;
import org.mcwonderland.uhc.game.timer.Timer;
import org.mcwonderland.uhc.game.timer.Timers;

public class PreStartState extends StartingState {

    public PreStartState(StateName name) {
        super(name);
    }

    @Override
    public Iterable<Timer> getTimers() {
        return Lists.newArrayList(
                Timers.START
        );
    }
}
