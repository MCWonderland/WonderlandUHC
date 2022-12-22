package org.mcwonderland.uhc.scoreboard;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mcwonderland.uhc.game.Game;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoreBoardUpdater {

    public static void start() {
        Common.runTimerAsync(1, new SidebarUpdater());
        addNewUpdate(1, SimpleScores::updateHeals);
        addNewUpdate(5, SimpleScores::updateNameTagColors);
    }


    public static void addNewUpdate(int tick, ScoreUpdateCallback scoreUpdateCallback) {
        Common.runTimerAsync(tick, () -> {
            for (SimpleScores score : SimpleScores.getAllScores())
                scoreUpdateCallback.update(score);
        });
    }


    @FunctionalInterface
    interface ScoreUpdateCallback {
        void update(SimpleScores score);
    }


    private static class SidebarUpdater extends BukkitRunnable {
        private int tick = 0;

        @Override
        public void run() {
            replaceScoreLinesGlobalVar();

            if (tick >= Game.getSettings().getScoreboardSettings().getScoreboardUpdateTick()) {
                SimpleScores.getAllScores().forEach(SimpleScores::updateSidebar);
                tick = 0;
            }

            tick++;
        }

        public void replaceScoreLinesGlobalVar() {
            SidebarTheme sidebarTheme = Game.getSettings().getScoreboardSettings().getSidebarTheme();

            sidebarTheme.getLines().forEach(scoreboardLines -> scoreboardLines.updateGlobalVariables());
        }
    }

}
