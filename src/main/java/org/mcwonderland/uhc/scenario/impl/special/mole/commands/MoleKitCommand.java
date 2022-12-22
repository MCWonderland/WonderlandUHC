package org.mcwonderland.uhc.scenario.impl.special.mole.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.mole.ScenarioMole;
import org.mcwonderland.uhc.scenario.impl.special.mole.SelectKitMenu;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;

import java.util.List;

@RequiredArgsConstructor
public class MoleKitCommand implements MoleCommand {

    private final ScenarioMole mole;
    private final SelectKitMenu menu;

    @Override
    public String getLabel() {
        return "kit";
    }

    @Override
    public void onCommand(Player player, List<String> args) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (ScenarioMole.isMole(uhcPlayer)) {
            if (!mole.isKitSelected(player))
                menu.open(player);
            else
                Chat.send(player, CommandSettings.Mole.KIT_TWICE);
        } else {
            Chat.send(player, ScenarioMole.getMoleCommandDeniedMessage());
        }
    }
}
