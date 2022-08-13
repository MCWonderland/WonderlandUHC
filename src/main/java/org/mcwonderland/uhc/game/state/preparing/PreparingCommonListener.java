package org.mcwonderland.uhc.game.state.preparing;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.game.state.share.CommonListener;
import org.mcwonderland.uhc.practice.Practice;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PreparingCommonListener extends CommonListener {

    private WonderlandUHC plugin = WonderlandUHC.getInstance();
    private Practice practice = plugin.getPractice();

    @Override
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = ( Player ) e.getEntity();

            if (!practice.isInPractice(player)) {
                super.onDamage(e);
                return;
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                super.onDamage(e);
                return;
            }

            if (e instanceof EntityDamageByEntityEvent) {
                Entity damager = (( EntityDamageByEntityEvent ) e).getDamager();

                if (damager instanceof Player) {
                    Player playerDamager = ( Player ) damager;

                    if (practice.isInPractice(playerDamager))
                        return;
                }
            }
        } else

            super.onDamage(e);
    }


    @Override
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!practice.isInPractice(player))
            super.onBlockBreak(e);
    }


}
