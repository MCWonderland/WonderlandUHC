package org.mcwonderland.uhc.game.state.share;

import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.mineacademy.fo.Common;

public abstract class MotdListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        if (!Settings.Misc.CHANGE_MOTD)
            return;

        e.setMotd(Common.colorize(getMotd()));
    }

    protected abstract String getMotd();

}
