package org.mcwonderland.uhc.command.impl.host.whitelist;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class WhitelistCommandGroup extends SimpleCommandGroup {

    public WhitelistCommandGroup(String labelAndAliases) {
        super(labelAndAliases);
    }

    @Override
    protected void registerSubcommands() {
        registerSubcommand(new AddCommand(this, "add"));
        registerSubcommand(new RemoveCommand(this, "remove"));
        registerSubcommand(new ListCommand(this, "list"));
        registerSubcommand(new ClearCommand(this, "clear"));
    }

    @Override
    protected boolean sendHelpIfNoArgs() {
        return true;
    }
}
