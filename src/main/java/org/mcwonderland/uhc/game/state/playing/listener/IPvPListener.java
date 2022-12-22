package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.List;

public class IPvPListener implements Listener {

    private Game game = Game.getGame();

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        if (e.getBucket().toString().contains("LAVA")) {
            if (!Game.getGame().isPvpEnabled()) {
                List<Entity> entidades = e.getPlayer().getNearbyEntities(5, 5, 5);
                for (Entity current : entidades) {
                    if (current instanceof Player) {
                        if (current != e.getPlayer()) {
                            e.setCancelled(true);
                            Chat.send(e.getPlayer(), Messages.Game.IPVP_LAVA);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        if (!game.isPvpEnabled() && e.getBlock().getType() == CompMaterial.FIRE.getMaterial()) {
            boolean iPvPDetected = player.getNearbyEntities(5, 5, 5).stream()
                    .anyMatch(entity -> entity instanceof Player && entity != player);

            if (iPvPDetected) {
                e.setCancelled(true);
                Chat.send(player, Messages.Game.IPVP_FIRE);
            }
        }
    }

}
