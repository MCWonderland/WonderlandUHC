package org.mcwonderland.uhc.command.scenarioMole;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class MoleCommandGroup extends SimpleCommandGroup {
    public MoleCommandGroup(String labelAndAliases) {
        super(labelAndAliases);
    }

    @Override
    protected void registerSubcommands() {
        registerSubcommand(new MoleChatCommand(this, "chat"));
        registerSubcommand(new MoleKitCommand(this, "kit"));
        registerSubcommand(new MoleListCommand(this, "list"));
        registerSubcommand(new MoleScsCommand(this, "scs"));
    }

    @Override
    protected boolean sendHelpIfNoArgs() {
        return true;
    }
}
