package org.mcwonderland.uhc.scenario.impl.damage;

import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * 2019-12-06 上午 01:15
 */
public class ScenarioFireLess extends ConfigBasedScenario implements Listener {

    public ScenarioFireLess(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onGamingEntityDamage(UHCPlayerDamageEvent e) {
        EntityDamageEvent.DamageCause cause = e.getCause();

        if (cause == EntityDamageEvent.DamageCause.LAVA
                || cause.toString().contains("FIRE"))
            e.setCancelled(true);
    }
}
