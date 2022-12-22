package org.mcwonderland.uhc.scenario.impl.disable;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

/**
 * 2019-12-06 上午 01:44
 */
public class ScenarioBowLess extends ConfigBasedScenario implements Listener {

    public ScenarioBowLess(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBow(EntityShootBowEvent e) {
        LivingEntity shooter = e.getEntity();

        if (shooter instanceof Player)
            e.setCancelled(true);
    }
}
