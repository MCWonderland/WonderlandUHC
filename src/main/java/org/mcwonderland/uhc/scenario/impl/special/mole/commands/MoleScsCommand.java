package org.mcwonderland.uhc.scenario.impl.special.mole.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.mole.ScenarioMole;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

@RequiredArgsConstructor
public class MoleScsCommand implements MoleCommand {

    private final ScenarioMole mole;

    @Override
    public String getLabel() {
        return "scs";
    }

    @Override
    public void onCommand(Player player, List<String> args) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        CommandHelper.checkGameStarted();

        if (ScenarioMole.isMole(uhcPlayer) && uhcPlayer.isAlive())
            sendCoords(player);
        else
            Chat.send(player, ScenarioMole.getMoleCommandDeniedMessage());
    }

    private void sendCoords(Player player) {
        Location location = player.getLocation();

        for (UHCPlayer uhcPlayer : ScenarioMole.getMoleList()) {
            Chat.send(uhcPlayer.getPlayer(), new SimpleReplacer(CommandSettings.Mole.SCS)
                    .replace("{player}", player.getName())
                    .replace("{x}", location.getBlockX())
                    .replace("{y}", location.getBlockY())
                    .replace("{z}", location.getBlockZ())
                    .getMessages());
        }
    }
}
