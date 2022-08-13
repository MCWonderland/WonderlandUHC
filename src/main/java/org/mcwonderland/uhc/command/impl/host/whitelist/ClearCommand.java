package org.mcwonderland.uhc.command.impl.host.whitelist;

import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;

class ClearCommand extends WhitelistSubCommand {

    protected ClearCommand(WhitelistCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("Clear whitelist.");
    }

    @Override
    protected void onCommand() {
        getWhitelist().clear();

        Chat.broadcastWithPerm(getPermission(), CommandSettings.Whitelist.CLEARED
                .replace("{player}", getPlayer().getName()));
    }
}
