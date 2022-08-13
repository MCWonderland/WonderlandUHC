package org.mcwonderland.uhc.listener;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.mineacademy.fo.Common;

public class BooleanEvents implements Listener {

    @EventHandler
    public void antiRain(WeatherChangeEvent event) {
        if (Settings.Misc.ANTI_RAIN && event.toWeatherState())
            event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UHCPlayer player = UHCPlayer.getUHCPlayer(e.getPlayer());

        

        Common.runLater(1, () -> {
            if (player.isOnline())
                player.checkHide();
        });
    }
}
