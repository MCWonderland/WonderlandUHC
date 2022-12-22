package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class SoloLines extends GameLines {

    public SoloLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer) {
        return super.replace(uhcPlayer, simpleReplacer)
                .replace("{kills}", uhcPlayer.getStats().kills);
    }
}
