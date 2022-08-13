package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.util.TeamModifier;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.List;

public class SplitTeamCommand extends SimpleSubCommand {

    protected SplitTeamCommand(UHCMainCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("[是否要先解散所有隊伍(true/false)]");
        setDescription("對玩家進行分隊。");
    }

    @Override
    protected void onCommand() {
        if (args.length == 1 && Boolean.parseBoolean(args[0])) {
            TeamModifier.resetTeams();
        }

        TeamModifier.splitTeams();
    }

    @Override
    protected List<String> tabComplete() {

        if (args.length == 1)
            return completeLastWord("false", "true");

        return super.tabComplete();
    }
}
