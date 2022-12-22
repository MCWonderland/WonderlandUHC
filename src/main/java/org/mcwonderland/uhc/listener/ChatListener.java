package org.mcwonderland.uhc.listener;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    //todo 重寫 (變成 ricipent?)
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public boolean onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
        String message = e.getMessage();

        e.setCancelled(true);
        uhcPlayer.chat(message);

        return true;
    }
}
