package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.model.tutorial.UHCConfigTutorial;
import org.mcwonderland.uhc.model.tutorial.UHCHostTutorial;
import org.mcwonderland.uhc.model.tutorial.model.Tutorial;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.List;

public class TutorialCommand extends SimpleSubCommand {

    protected TutorialCommand(UHCMainCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("<config|host>");
        setMinArguments(1);
        setDescription("學習如何主持與設定UHC。");
        setPermission(UHCPermission.COMMAND_UHC_TUTORIAL.toString());
    }

    @Override
    protected void onCommand() {
        getTutorial().start();
    }

    private Tutorial getTutorial() {
        switch (args[0]) {
            case "config":
                return new UHCConfigTutorial(getPlayer());
            case "host":
                return new UHCHostTutorial(getPlayer());
        }

        returnTell("&c找不到此教學。");
        return null;
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1)
            return completeLastWord("config", "host");

        return null;
    }
}
