package org.mcwonderland.uhc.practice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.fo.Common;

public class CommonPracticeListener implements Listener {

    private Practice practice;

    public CommonPracticeListener(Practice practice) {
        this.practice = practice;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (practice.isInPractice(player))
            e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        if (practice.isInPractice(player)) {
            e.getDrops().clear();
            player.setHealth(player.getMaxHealth());

            Common.runLater(3, () -> practice.stuff(player));

            if (killer != null)
                killer.setHealth(killer.getMaxHealth());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (practice.isInPractice(player))
            practice.quit(player);
    }

}
