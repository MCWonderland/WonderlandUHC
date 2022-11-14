package org.mcwonderland.uhc.game;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.mcwonderland.uhc.events.UHCGameTimerUpdateEvent;
import org.mcwonderland.uhc.game.timer.Timer;
import org.mineacademy.fo.Common;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameTimerRunnable implements Runnable {
    public static int tick = 1;
    public static int totalSecond = 0;
    public static boolean RUN = false;

    public static void start() {
        Common.runTimer(1, new GameTimerRunnable());
    }

    @Override
    public void run() {
        if (!RUN)
            return;

        if (tick > 19) {
            totalSecond++;
            tick = 0;
            Bukkit.getPluginManager().callEvent(new UHCGameTimerUpdateEvent(Game.getGame(), totalSecond));
        }

        for (Timer timer : Game.getGame().getCurrentState().getTimers()) {
            if (tick % timer.runTick() == 0)
                timer.run();
        }

        tick++;
    }
}
