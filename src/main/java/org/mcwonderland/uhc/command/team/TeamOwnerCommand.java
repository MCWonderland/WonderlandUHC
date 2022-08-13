package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;

abstract class TeamOwnerCommand extends TeamSubCommand {

    protected TeamOwnerCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    @Override
    protected final void onCommand() {
        UHCPlayer uhcPlayer = getUhcPlayer();

        checkBoolean(getTeam().getOwner() == uhcPlayer, CommandSettings.Team.NOT_OWNER);

        onOwnerCommand();
    }

    protected abstract void onOwnerCommand();
}
