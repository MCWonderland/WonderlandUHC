package org.mcwonderland.uhc.game.timer;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameTimerRunnable;
import org.mcwonderland.uhc.game.settings.sub.UHCTimerSettings;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Chat;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.model.SimpleReplacer;

public abstract class Countdown extends SecondTimer {

    @Override
    public final void run() {
        int difference = getToggleTimer() - GameTimerRunnable.totalSecond;

        if (difference > 0) {
            onCountdown(difference);
            broadcastCountdown(difference);
        } else if (difference == 0) {
            execute();
            broadcastRun();
        }
    }

    public abstract int getToggleTimer();

    @Nullable
    public abstract String getCountdownBroadcast();

    @Nullable
    public abstract String getToggledBroadcast();

    protected abstract void execute();

    public void onCountdown(int untilEnable) {

    }

    protected final UHCTimerSettings getTimerSettings() {
        return Game.getSettings().getTimer();
    }

    private final void broadcastCountdown(int untilEnable) {
        if (Settings.Game.ANNOUNCES_SECONDS.contains(untilEnable)) {
            String broadcast = getCountdownBroadcast();

            if (broadcast != null)
                Chat.broadcast(new SimpleReplacer(broadcast)
                        .replaceTime(untilEnable)
                        .getMessages());
        }
    }

    private final void broadcastRun() {
        String broadcast = getToggledBroadcast();

        if (broadcast != null)
            Chat.broadcast(broadcast);
    }
}
