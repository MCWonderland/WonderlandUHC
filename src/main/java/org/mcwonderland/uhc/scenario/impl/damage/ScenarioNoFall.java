package org.mcwonderland.uhc.scenario.impl.damage;

import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * 2019-12-07 下午 01:15
 */
public class ScenarioNoFall extends ConfigBasedScenario implements Listener {

    public ScenarioNoFall(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onGamingEntityDamage(UHCPlayerDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);
    }
}
