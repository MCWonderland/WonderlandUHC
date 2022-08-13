package org.mcwonderland.uhc.command.impl.host.whitelist;

import org.mcwonderland.uhc.game.Game;
import org.mineacademy.fo.collection.PlayerCollection;
import org.mineacademy.fo.command.SimpleSubCommand;

abstract class WhitelistSubCommand extends SimpleSubCommand {

    protected WhitelistSubCommand(WhitelistCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    public PlayerCollection getWhitelist() {
        return Game.getGame().getWhiteList();
    }

}
