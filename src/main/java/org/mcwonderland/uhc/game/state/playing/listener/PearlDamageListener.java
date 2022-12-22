package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PearlDamageListener implements Listener {

    @EventHandler
    public void enderPearlDamage(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (!Game.getSettings().isEnderPearlDamage()) {
                p.setNoDamageTicks(1);
                e.setCancelled(true);
                p.teleport(e.getTo());
            }
        }
    }

}
