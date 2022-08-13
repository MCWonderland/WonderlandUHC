package org.mcwonderland.uhc.model.freeze;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mineacademy.fo.Common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SitFreeze implements FreezeMode, Listener {

    private final Map<UUID, Entity> sitPlayers = new HashMap<>();

    public SitFreeze() {
        Bukkit.getPluginManager().registerEvents(this, WonderlandUHC.getInstance());
    }

    @Override
    public void freeze(Player player) {
        Common.runLater(5, () -> {
            Pig pig = player.getWorld().spawn(player.getLocation(), Pig.class);
            pig.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1), false);
            pig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 255), false);
            Extra.noAIAndSilent(pig);
            pig.setAdult();
            pig.setPassenger(player);
            sitPlayers.put(player.getUniqueId(), pig);
        });

    }

    @Override
    public void unFreeze(Player player) {
        Entity entity = sitPlayers.remove(player.getUniqueId());

        if (entity != null)
            entity.remove();
    }

    @EventHandler
    public void onVehicleEnterEvent(VehicleEnterEvent e) {
        if (!(e.getEntered() instanceof Player))
            return;

        Player p = (Player) e.getEntered();

        if (!isFreezing(p) && !GameUtils.isGamingPlayer(p))
            e.setCancelled(true);
    }

    @EventHandler
    public void onVehicleExit(VehicleExitEvent e) {
        if (isFreezing(e.getExited()))
            e.setCancelled(true);
    }

    private boolean isFreezing(Entity entity) {
        return (entity instanceof Player) && sitPlayers.containsKey(entity.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (isFreezing(player))
            unFreeze(player);
    }
}
