package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;

class PromoteCommand extends TeamOwnerCommand {

    protected PromoteCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("將指定玩家升格為隊長。");
        setPermission(UHCPermission.COMMAND_TEAM_PROMOTE.toString());
    }

    @Override
    protected void onOwnerCommand() {
        checkModeAndGameStatus();

        UHCPlayer uhcPlayer = CommandHelper.findUHCPlayer(args[0]);
        UHCTeam team = getTeam();

        checkExecuteSelf(uhcPlayer);
        checkInTeam(uhcPlayer);

        team.promote(uhcPlayer);
    }
}
