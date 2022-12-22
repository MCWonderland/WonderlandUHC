package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerStateListener implements Listener {

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        Player player = ( Player ) e.getEntity();
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (uhcPlayer.isDead()) {
            e.setFoodLevel(20);
            return;
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        boolean isGamingEntity = GameUtils.isGamingPlayer(e.getEntity());

        if (isGamingEntity && isNaturalRegen(e.getRegainReason())) {
            e.setCancelled(true);
        }
    }

    private boolean isNaturalRegen(EntityRegainHealthEvent.RegainReason reason) {
        return reason == EntityRegainHealthEvent.RegainReason.SATIATED
                || reason == EntityRegainHealthEvent.RegainReason.REGEN;
    }

}
