package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.state.share.MotdListener;
import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayingMotdListener extends MotdListener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        super.onServerListPing(e);
    }

    @Override
    protected String getMotd() {
        return Messages.Motd.PLAYING
                .replace("{remaining}", UHCPlayers.countOf(uhcPlayer -> !uhcPlayer.isDead()) + "");
    }
}
