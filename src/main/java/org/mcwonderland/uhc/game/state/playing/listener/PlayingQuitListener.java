package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.state.share.QuitListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayingQuitListener extends QuitListener {


    @Override
    public void onPlayerQuit(PlayerQuitEvent e) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(e.getPlayer());
        uhcPlayer.getEventHandler().onQuit(e);
    }

}
