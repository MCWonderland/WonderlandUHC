package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.UHCPermission;

class LeaveCommand extends TeamSubCommand {

    protected LeaveCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("離開目前所處隊伍。");
        setPermission(UHCPermission.COMMAND_TEAM_LEAVE.toString());
    }

    @Override
    protected void onCommand() {
        checkModeAndGameStatus();

        getTeam().leave(getUhcPlayer());
    }
}
