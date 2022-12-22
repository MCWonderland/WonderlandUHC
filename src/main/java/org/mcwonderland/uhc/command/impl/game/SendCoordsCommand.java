package org.mcwonderland.uhc.command.impl.game;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.model.SimpleReplacer;

/**
 * 2019-11-27 下午 01:05
 */
public class SendCoordsCommand extends SimpleCommand {

    public SendCoordsCommand(String label) {
        super(label);

        setPermission(UHCPermission.COMMAND_SENDCOORDS.toString());
        setMinArguments(0);
        setDescription("向隊友發送座標。");
    }

    @Override
    protected void onCommand() {
        Player player = getPlayer();
        UHCTeam team = UHCTeam.getTeam(player);

        CommandHelper.checkGameStarted();
        CommandHelper.checkGamingPlayer(player);

        Location location = player.getLocation();

        team.sendMessage(new SimpleReplacer(CommandSettings.SendCoords.FORMAT)
                .replace("{teamname}", team.getName())
                .replace("{color}", team.getColor())
                .replace("{player}", player.getName())
                .replace("{x}", location.getBlockX())
                .replace("{y}", location.getBlockY())
                .replace("{z}", location.getBlockZ())
                .getMessages()
        );

        Extra.sound(team.getAlivePlayers(), Sounds.Commands.SEND_COORDS);
    }
}
