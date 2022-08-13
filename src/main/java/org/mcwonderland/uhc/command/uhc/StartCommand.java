package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.game.Game;
import org.mineacademy.fo.command.SimpleSubCommand;

public class StartCommand extends SimpleSubCommand {

    protected StartCommand(UHCMainCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("<config|host>");
        setDescription("開始遊戲！");
    }

    @Override
    protected void onCommand() {
        Game.getGame().tryToStart(sender);
    }
}
