package org.mcwonderland.uhc.scenario.impl.disable;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ScenarioRodLess extends ConfigBasedScenario implements Listener {

    public ScenarioRodLess(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        Player shooter = PlayerUtils.getShooter(projectile);

        if (shooter == null)
            return;

        if (projectile instanceof FishHook)
            e.setCancelled(true);
    }
}
