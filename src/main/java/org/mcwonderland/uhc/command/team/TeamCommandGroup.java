package org.mcwonderland.uhc.command.team;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class TeamCommandGroup extends SimpleCommandGroup {
    public TeamCommandGroup(String labelAndAliases) {
        super(labelAndAliases);
    }

    @Override
    protected void registerSubcommands() {
        registerSubcommand(new ChatCommand(this, "chat"));
        registerSubcommand(new CreateCommand(this, "create"));
        registerSubcommand(new DisbandCommand(this, "disband"));
        registerSubcommand(new InviteCommand(this, "invite"));
        registerSubcommand(new JoinCommand(this, "join"));
        registerSubcommand(new LeaveCommand(this, "leave"));
        registerSubcommand(new KickCommand(this, "kick"));
        registerSubcommand(new ListCommand(this, "list"));
        registerSubcommand(new PromoteCommand(this, "promote"));
        registerSubcommand(new PublicCommand(this, "public"));
        registerSubcommand(new SettingsCommand(this, "settings"));
    }

    @Override
    protected boolean sendHelpIfNoArgs() {
        return true;
    }
}
