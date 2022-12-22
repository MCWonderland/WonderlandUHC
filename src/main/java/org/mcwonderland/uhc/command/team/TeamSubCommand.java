package org.mcwonderland.uhc.command.team;

import lombok.Getter;
import org.mcwonderland.uhc.api.enums.TeamSplitMode;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Messages;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

@Getter
public abstract class TeamSubCommand extends SimpleSubCommand {

    protected TeamSubCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    protected final UHCTeam getTeam() {
        return getTeam(getUhcPlayer());
    }

    protected final UHCPlayer getUhcPlayer(){
        return UHCPlayer.getUHCPlayer(getPlayer());
    }

    protected final UHCTeam getTeam(UHCPlayer uhcPlayer) {
        checkBoolean(uhcPlayer.getTeam() != null,
                uhcPlayer == getUhcPlayer() ?
                        CommandSettings.Team.YOU_DONT_HAVE_TEAM :
                        CommandSettings.Team.PLAYER_HAS_NO_TEAM
        );

        return uhcPlayer.getTeam();
    }

    protected final void checkChosenMode() {
        if (Game.getSettings().getTeamSettings().getTeamSplitMode() != TeamSplitMode.CHOSEN)
            returnTell(Messages.Team.ONLY_IN_CHOSEN_MODE);
    }

    protected final void checkModeAndGameStatus() {
        checkChosenMode();
        CommandHelper.checkWaiting();
    }

    protected final void checkExecuteSelf(UHCPlayer target) {
        CommandHelper.checkExecuteSelf(getPlayer(), target.getPlayer());
    }

    protected final void checkAlreadyInTeam() {
        checkBoolean(getUhcPlayer().getTeam() == null, CommandSettings.Team.ALREADY_HAS_ONE);
    }

    protected final void checkInTeam(UHCPlayer target) {
        checkBoolean(getTeam() == target.getTeam(), CommandSettings.Team.PLAYER_NOT_IN_TEAM);
    }

    protected final void checkFull(UHCTeam uhcTeam) {
        checkBoolean(!uhcTeam.isFull(), Messages.Team.FULL_MSG);
    }

}
