package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.UHCPermission;

class DisbandCommand extends TeamOwnerCommand {

    protected DisbandCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("解散目前隊伍。");
        setPermission(UHCPermission.COMMAND_TEAM_DISBAND.toString());
    }

    @Override
    protected void onOwnerCommand() {
        checkModeAndGameStatus();

        getTeam().disband();
    }
}
