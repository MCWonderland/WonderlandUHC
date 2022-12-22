package org.mcwonderland.uhc.game.state;

import org.mcwonderland.uhc.game.StateName;
import org.mcwonderland.uhc.game.timer.Timer;
import org.bukkit.entity.Player;

import java.util.List;

public interface GameState {

    void init();

    void start();

    void end();

    StateName getName();

    Iterable<Timer> getTimers();

    List<String> getScoreboard(Player player);
}
