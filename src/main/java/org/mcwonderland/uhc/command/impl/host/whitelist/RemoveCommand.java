package org.mcwonderland.uhc.command.impl.host.whitelist;

import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;

class RemoveCommand extends WhitelistSubCommand {

    protected RemoveCommand(WhitelistCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setMinArguments(1);
        setUsage("<player>");
        setDescription("Remove player from list.");
    }

    @Override
    protected void onCommand() {
        String toRemove = args[0];

        checkBoolean(getWhitelist().contains(toRemove),
                CommandSettings.Whitelist.ALREADY_REMOVED.replace("{player}", toRemove));

        getWhitelist().remove(toRemove);

        Chat.broadcastWithPerm(getPermission(), CommandSettings.Whitelist.REMOVED
                .replace("{player}", toRemove)
                .replace("{op}", getSender().getName()));
    }
}
