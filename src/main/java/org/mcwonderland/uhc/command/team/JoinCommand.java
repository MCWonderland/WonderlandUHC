package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;

class JoinCommand extends TeamSubCommand {

    protected JoinCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setMinArguments(1);
        setUsage("<玩家>");
        setDescription("接受邀請並加入隊伍。");
    }

    @Override
    protected void onCommand() {
        checkModeAndGameStatus();

        UHCPlayer uhcPlayer = getUhcPlayer();
        UHCTeam team = getTeam(CommandHelper.findUHCPlayer(args[0]));

        checkFull(team);

        if (!team.isOpenJoin())
            checkBoolean(team.isInvited(uhcPlayer), CommandSettings.Team.Join.NO_INVITATION);

        team.join(uhcPlayer);
    }
}
