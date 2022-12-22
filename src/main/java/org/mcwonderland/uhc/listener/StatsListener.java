package org.mcwonderland.uhc.listener;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.fo.Common;

public class StatsListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(e.getPlayer());

        Common.runLaterAsync(() -> WonderlandUHC.getStatsStorage().save(uhcPlayer));
    }


}
