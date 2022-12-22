package org.mcwonderland.uhc.scenario.impl.special.mole.commands;

import org.bukkit.entity.Player;

import java.util.List;

public interface MoleCommand {
    String getLabel();
    void onCommand(Player player, List<String> args);
}
