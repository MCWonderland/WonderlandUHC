package org.mcwonderland.uhc.command.scenarioMole;

import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class MoleKitCommand extends SimpleSubCommand {
    public MoleKitCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("[訊息]");
        setDescription("對間諜們發送訊息");
    }
    @Override
    protected void onCommand() {

    }
}
