package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mineacademy.fo.model.SimpleReplacer;

class KickCommand extends TeamOwnerCommand {

    protected KickCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setMinArguments(1);
        setUsage("<玩家>");
        setDescription("將指定玩家從隊伍中剔除。");
    }

    @Override
    protected void onOwnerCommand() {
        checkModeAndGameStatus();

        UHCTeam team = getTeam();
        UHCPlayer target = CommandHelper.findUHCPlayer(args[0]);

        checkExecuteSelf(target);
        checkInTeam(target);

        team.sendMessage(new SimpleReplacer(CommandSettings.Team.Kick.KICKED)
                .replace("{player}", target.getName())
                .toArray());

        team.leave(target);
    }
}
