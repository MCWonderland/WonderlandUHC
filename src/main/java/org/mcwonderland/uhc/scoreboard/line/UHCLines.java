package org.mcwonderland.uhc.scoreboard.line;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.Remain;

import java.util.List;

public abstract class UHCLines extends ScoreLines {

    public UHCLines(List<String> lines) {
        super(lines);
    }

    @Override
    protected SimpleReplacer replace(UHCPlayer uhcPlayer, SimpleReplacer simpleReplacer) {
        return simpleReplacer
                .replace("{ign}", uhcPlayer.getName());
    }

    @Override
    protected SimpleReplacer replaceGlobal(SimpleReplacer simpleReplacer) {
        return simpleReplacer
                .replace("{online_players}", Remain.getOnlinePlayers().size())
                .replace("{host}", Game.getGame().getHost());
    }
}
