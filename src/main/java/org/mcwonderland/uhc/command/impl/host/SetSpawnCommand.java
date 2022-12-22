package org.mcwonderland.uhc.command.impl.host;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.settings.spawn.Spawns;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class SetSpawnCommand extends SimpleCommand {

    public SetSpawnCommand(String label) {
        super(label);

        setMinArguments(0);
        setDescription("設定大廳重生點。");
        setPermission(UHCPermission.COMMAND_SET_SPAWN.toString());
    }

    @Override
    protected void onCommand() {
        Player player = getPlayer();

        Spawns.getLobbySpawn().updateLocation(player.getLocation());
        tell(CommandSettings.SetSpawn.SPAWN_SAVED);
        Extra.sound(player, Sounds.Commands.SET_SPAWN);
    }
}
