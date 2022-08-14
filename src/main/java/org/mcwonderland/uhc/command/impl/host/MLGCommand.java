package org.mcwonderland.uhc.command.impl.host;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class MLGCommand extends SimpleCommand {

    public MLGCommand(String label) {
        super(label);

        setMinArguments(1);
        setUsage("<玩家>");
        setDescription("將玩家傳送到你的位置，開始MLG挑戰。");
        setPermission(UHCPermission.COMMAND_MLG.toString());
    }

    @Override
    protected void onCommand() {
        Player player = getPlayer();
        Player target = findPlayer(args[0]);

        Location mlgLocation = player.getLocation();

        Extra.sound(mlgLocation, Sounds.Commands.MLG);
        target.teleport(mlgLocation);
    }
}
