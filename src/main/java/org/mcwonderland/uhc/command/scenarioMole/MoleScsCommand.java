package org.mcwonderland.uhc.command.scenarioMole;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.ScenarioMole;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleReplacer;

public class MoleScsCommand extends SimpleSubCommand {

    public MoleScsCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("");
        setMinArguments(0);
        setDescription("對間諜們發送座標");
    }

    @Override
    protected void onCommand() {
        UHCPlayer player = UHCPlayer.getUHCPlayer(getPlayer());

        if (ScenarioMole.getMoleList().contains(player) && player.isAlive())
            sendCoords();
        else
            Chat.send(getSender(), ScenarioMole.getMoleCommandDeniedMessage());
    }

    private void sendCoords() {
        Player player = getPlayer();
        Location location = player.getLocation();

        for (UHCPlayer uhcPlayer : ScenarioMole.getMoleList()) {
            Chat.send(uhcPlayer.getPlayer(), new SimpleReplacer(CommandSettings.Mole.Scs)
                            .replace("{player}", player.getName())
                            .replace("{x}", location.getBlockX())
                            .replace("{y}", location.getBlockY())
                            .replace("{z}", location.getBlockZ())
                            .getMessages());
        }
    }
}
