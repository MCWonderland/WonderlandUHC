package org.mcwonderland.uhc.command.team;

class LeaveCommand extends TeamSubCommand {

    protected LeaveCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("離開目前所處隊伍。");
    }

    @Override
    protected void onCommand() {
        checkModeAndGameStatus();

        getTeam().leave(getUhcPlayer());
    }
}
