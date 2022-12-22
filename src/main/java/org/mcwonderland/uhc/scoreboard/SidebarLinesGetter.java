package org.mcwonderland.uhc.scoreboard;

import org.mcwonderland.uhc.game.Game;
import org.bukkit.entity.Player;

import java.util.List;

class SidebarLinesGetter {

    public List<String> getBoardLines(Player player) {
        Game game = Game.getGame();
        return game.getCurrentState().getScoreboard(player);
    }

}
