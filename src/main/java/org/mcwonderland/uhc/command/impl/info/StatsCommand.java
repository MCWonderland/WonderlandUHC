package org.mcwonderland.uhc.command.impl.info;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.menu.impl.game.StatsMenu;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class StatsCommand extends SimpleCommand {

    public StatsCommand(String label) {
        super(label);

        setMinArguments(0);
        setUsage("[玩家]");
        setDescription("查看玩家數據。");
        setPermission(UHCPermission.COMMAND_STATS.toString());
    }

    @Override
    protected void onCommand() {
        Player player;

        if (args.length >= 1)
            player = findPlayer(args[0]);
        else
            player = getPlayer();

        new StatsMenu(player).displayTo(player);
    }
}