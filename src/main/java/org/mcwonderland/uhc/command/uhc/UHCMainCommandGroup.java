package org.mcwonderland.uhc.command.uhc;

import org.mineacademy.fo.command.SimpleCommandGroup;

/**
 * 2019-11-24 下午 12:43
 */
public class UHCMainCommandGroup extends SimpleCommandGroup {
    public UHCMainCommandGroup(String labelAndAliases) {
        super(labelAndAliases);
    }

    @Override
    protected void registerSubcommands() {
        registerSubcommand(new ReloadCommand(this, "reload|rl"));

        registerSubcommand(new ChooseWorldCommand(this, "choose"));
        registerSubcommand(new EditCommand(this, "edit"));
        registerSubcommand(new RegenWorldCommand(this, "regen"));
        registerSubcommand(new ResetTeamCommand(this, "resetteam"));
        registerSubcommand(new SetHostCommand(this, "sethost"));
        registerSubcommand(new SplitTeamCommand(this, "splitteam"));
        registerSubcommand(new SwitchTeamCommand(this, "switchteam"));
        registerSubcommand(new StopCommand(this, "stop"));
        registerSubcommand(new TpUHCWorldCommand(this, "tp"));
        registerSubcommand(new StartCommand(this, "start"));

        registerSubcommand(new TutorialCommand(this, "tutorial"));
    }

}
