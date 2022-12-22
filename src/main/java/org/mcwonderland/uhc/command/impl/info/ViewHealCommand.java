package org.mcwonderland.uhc.command.impl.info;

import org.bukkit.entity.Player;
import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class ViewHealCommand extends SimpleCommand {

    public ViewHealCommand(String label) {
        super(label);

        setMinArguments(1);
        setUsage("<玩家>");
        setDescription("查看玩家血量。");
        setPermission(UHCPermission.COMMAND_VIEWHEAL.toString());
    }

    @Override
    protected void onCommand() {
        Player target = findPlayer(args[0]);
        tell(CommandSettings.Heal.FORMAT
                .replace("{player}", target.getName())
                .replace("{heal}", "" + Extra.formatHealth(target.getHealth())));
    }
}