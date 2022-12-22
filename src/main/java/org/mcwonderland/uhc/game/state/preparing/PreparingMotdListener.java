package org.mcwonderland.uhc.game.state.preparing;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.state.share.MotdListener;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Extra;

public class PreparingMotdListener extends MotdListener {

    @Override
    protected String getMotd() {
        String message = "";
        switch (CacheSaver.getLoadingStatus()) {
            case CONFIGURING:
                message = Messages.Motd.CONFIGURING;
                break;
            case GENERATING:
                message = Messages.Motd.GENERATING;
                break;
            case DONE:
                message = Messages.Motd.WAITING
                        .replace("{online}", "" + Extra.getOnlinePlayers())
                        .replace("{max}", "" + Game.getSettings().getMaxPlayers());
                break;
        }

        return message;
    }
}
