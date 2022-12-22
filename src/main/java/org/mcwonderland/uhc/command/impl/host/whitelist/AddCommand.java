package org.mcwonderland.uhc.command.impl.host.whitelist;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;

class AddCommand extends WhitelistSubCommand {

    protected AddCommand(WhitelistCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setMinArguments(1);
        setUsage("<player1> <player2>...");
        setDescription("Add players to whitelist.");
    }

    @Override
    protected void onCommand() {
        String[] names = rangeArgs(0, this.args.length);

        for (String name : names)
            add(name);
    }

    private void add(String name) {

        if (getWhitelist().contains(name)) {
            tell(CommandSettings.Whitelist.ALREADY_ADDED.replace("{player}", name));
            return;
        }


        getWhitelist().add(name);

        Chat.broadcastWithPerm(getPermission(), CommandSettings.Whitelist.ADDED
                .replace("{player}", name)
                .replace("{op}", getSender().getName()));
    }
}
