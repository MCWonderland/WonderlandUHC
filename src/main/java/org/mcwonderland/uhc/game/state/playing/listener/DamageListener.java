package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageByEntityEvent;
import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameManager;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.model.InvinciblePlayer;
import org.mcwonderland.uhc.util.GameUtils;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onGamingEntityDamageByGamingPlayer(UHCPlayerDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player))
            return;

        UHCPlayer damaged = e.getUhcPlayer();
        UHCPlayer shooter = UHCPlayer.getFromEntity(e.getDamager());

        handleDamage(damaged, shooter, e);
    }

    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent e) {
        if (!GameUtils.isGamingPlayer(e.getAttacker()))
            e.setCancelled(true);
    }


    @EventHandler
    public void onArrowDamage(UHCPlayerDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Arrow))
            return;

        UHCPlayer damaged = e.getUhcPlayer();
        Player shooter = PlayerUtils.getShooter(e.getDamager());

        if (shooter == null || shooter == damaged)
            return;

        handleDamage(damaged, UHCPlayer.getUHCPlayer(shooter), e);
    }

    @EventHandler
    public void onGamingEntityDamage(UHCPlayerDamageEvent e) {
        if (!Game.getGame().isDamageEnabled())
            e.setCancelled(true);

        else if (InvinciblePlayer.isInvincible(e.getUhcPlayer()))
            e.setCancelled(true);
    }


    private void handleDamage(UHCPlayer uhcPlayer, UHCPlayer damager, UHCPlayerDamageByEntityEvent e) {

        if (!Game.getGame().isPvpEnabled())
            e.setCancelled(true);

        else if (GameManager.isTeamFireDisabled(uhcPlayer, damager))
            e.setCancelled(true);

        if (!e.isCancelled()) {
            InvinciblePlayer.removeInvincible(damager);
        }
    }

}
