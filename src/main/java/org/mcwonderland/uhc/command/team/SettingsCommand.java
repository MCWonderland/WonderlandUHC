package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.menu.impl.game.TeamSettingsMenu;

class SettingsCommand extends TeamOwnerCommand {

    protected SettingsCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("開啟隊伍設定介面。");
        setPermission(UHCPermission.COMMAND_TEAM_SETTINGS.toString());
    }

    @Override
    protected void onOwnerCommand() {
        CommandHelper.checkWaiting();

        new TeamSettingsMenu(getTeam()).displayTo(getPlayer());
    }
}
