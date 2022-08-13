package org.mcwonderland.uhc.command.impl.host.whitelist;

import org.mcwonderland.uhc.settings.CommandSettings;
import org.mineacademy.fo.model.SimpleReplacer;

class ListCommand extends WhitelistSubCommand {

    protected ListCommand(WhitelistCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setDescription("Show whitelisted players.");
    }

    @Override
    protected void onCommand() {
        tell(new SimpleReplacer(CommandSettings.Whitelist.LIST)
                .replace("{players}", getWhitelist())
                .buildList());
    }
}
