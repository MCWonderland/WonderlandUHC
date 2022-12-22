package org.mcwonderland.uhc.practice;

import org.bukkit.entity.Player;

public interface Practice {

    void setup();

    void join(Player player);

    void quit(Player player);

    void stuff(Player player);

    boolean isInPractice(Player player);

    Iterable<Player> getPlayers();
}
