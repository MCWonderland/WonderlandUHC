package org.mcwonderland.uhc.game.state.starting;

import org.mcwonderland.uhc.game.state.share.MotdListener;
import org.mcwonderland.uhc.settings.Messages;

public class StartingMotdListener extends MotdListener {

    @Override
    protected String getMotd() {
        return Messages.Motd.STARTING;
    }
}
