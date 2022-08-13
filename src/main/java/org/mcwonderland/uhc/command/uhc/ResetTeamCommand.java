package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.util.TeamModifier;
import org.mineacademy.fo.command.SimpleSubCommand;

public class ResetTeamCommand extends SimpleSubCommand {

    protected ResetTeamCommand(UHCMainCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("強制解散所有隊伍。");
    }

    @Override
    protected void onCommand() {
        CommandHelper.checkWaiting();

        TeamModifier.resetTeams();
    }
}
