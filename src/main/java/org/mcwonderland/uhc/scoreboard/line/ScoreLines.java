package org.mcwonderland.uhc.scoreboard.line;


import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public abstract class ScoreLines {

    private final List<String> lines;
    private SimpleReplacer currentGlobalReplacedLines;

    public ScoreLines(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getFor(UHCPlayer uhcPlayer) {
        return replace(uhcPlayer, currentGlobalReplacedLines.clone()).buildList();
    }

    public void updateGlobalVariables() {
        currentGlobalReplacedLines = replaceGlobal(new SimpleReplacer(lines));
    }

    protected abstract SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer);

    protected abstract SimpleReplacer replaceGlobal(SimpleReplacer simpleReplacer);
}
