package org.mcwonderland.uhc.scenario.impl.consume;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Collection;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioPotionLess extends ConfigBasedScenario implements Listener {

    public ScenarioPotionLess(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDrinkPotion(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == CompMaterial.POTION.getMaterial())
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBrew(BrewEvent e) {
        e.setCancelled(true);
    }

    @Override
    protected Collection<Listener> initListeners() {
        Collection<Listener> listeners = super.initListeners();

        if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_14))
            listeners.add(new Listener_1_14());

        if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_9))
            listeners.add(new Listener_1_9());

        return listeners;
    }

    class Listener_1_9 implements Listener {
        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onEntityPotionEffect(org.bukkit.event.entity.LingeringPotionSplashEvent e) {
            e.setCancelled(true);
        }
    }

    class Listener_1_14 implements Listener {
        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onEntityPotionEffect(org.bukkit.event.entity.EntityPotionEffectEvent e) {
            if (e.getCause().toString().contains("POTION"))
                e.setCancelled(true);
        }
    }
}
