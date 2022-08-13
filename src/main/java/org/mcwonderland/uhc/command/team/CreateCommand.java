package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.model.SimpleReplacer;

class CreateCommand extends TeamSubCommand {

    protected CreateCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("創建隊伍。");
    }

    @Override
    protected void onCommand() {
        checkModeAndGameStatus();
        checkAlreadyInTeam();

        UHCTeam.createTeamIfNotExist(getUhcPlayer());
        tell(new SimpleReplacer(CommandSettings.Team.Create.CREATED)
                .replace("{cmd}", getLabel())
                .toArray());

        Extra.sound(getPlayer(), Sounds.Commands.TEAM_CREATED);
    }
}
