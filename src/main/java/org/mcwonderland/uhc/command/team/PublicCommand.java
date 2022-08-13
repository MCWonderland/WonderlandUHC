package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.game.UHCTeam;

class PublicCommand extends TeamOwnerCommand {

    protected PublicCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("公開隊伍讓其他玩家自由加入。");
    }

    @Override
    protected void onOwnerCommand() {
        checkModeAndGameStatus();

        UHCTeam team = getTeam();
        team.setOpenJoin(!team.isOpenJoin());
    }
}
