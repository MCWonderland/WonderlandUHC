package org.mcwonderland.uhc.game.timer;

import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.game.GameTimerRunnable;
import org.mcwonderland.uhc.game.timer.impl.BorderSizeUpdater;
import org.mcwonderland.uhc.game.timer.impl.RelogExpireChecker;
import org.mcwonderland.uhc.game.timer.impl.ScatterHandler;
import org.mcwonderland.uhc.game.timer.impl.countdown.*;
import org.mcwonderland.uhc.game.timer.impl.countdown.*;
import org.mineacademy.fo.TimeUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class Timers {
    private Set<Countdown> gameTimers = new HashSet<>();

    public Countdown LOBBY = new LobbyCountdown();
    public Countdown START = new StartCountdown();

    public ScatterHandler SCATTER = new ScatterHandler();

    public Countdown DAMAGE = new DamageCountdown();
    public Countdown FINAL_HEAL = new FinalHealCountdown();
    public Countdown PVP = new PvpCountdown();
    public Countdown BORDER = new BorderCountdown();
    public Countdown NETHER_CLOSE = new NetherCloseCountdown();
    public Timer RELOG_CHECKER = new RelogExpireChecker();
    public Timer BORDER_SIZE_UPDATER = new BorderSizeUpdater();

    public Set<Countdown> getGameCountdowns() {
        return Sets.newHashSet(gameTimers);
    }

    public int getSecondsUntilEnable(Countdown countdown) {
        return countdown.getToggleTimer() - GameTimerRunnable.totalSecond;
    }

    public String getUntilEnableFormat(Countdown countdown) {
        return TimeUtil.formatTime(getSecondsUntilEnable(countdown));
    }

    static {
        gameTimers.addAll(Arrays.asList(
                DAMAGE,
                FINAL_HEAL,
                PVP,
                BORDER,
                NETHER_CLOSE));
    }
}
