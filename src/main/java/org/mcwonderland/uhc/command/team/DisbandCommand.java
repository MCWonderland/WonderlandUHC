package org.mcwonderland.uhc.command.team;

class DisbandCommand extends TeamOwnerCommand {

    protected DisbandCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("解散目前隊伍。");
    }

    @Override
    protected void onOwnerCommand() {
        checkModeAndGameStatus();

        getTeam().disband();
    }
}
