package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

class SwitchTeamCommand extends SimpleSubCommand {

    public SwitchTeamCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setMinArguments(2);
        setUsage("<目標> <要切換到哪位玩家的隊伍>");
        setDescription("切換玩家的隊伍。");
    }

    @Override
    protected void onCommand() {
        UHCPlayer target = CommandHelper.findUHCPlayer(args[0]);
        UHCPlayer toSwitch = CommandHelper.findUHCPlayer(args[1]);
        UHCTeam team = toSwitch.getTeam();

        team.join(target);
    }
}
