package org.mcwonderland.uhc.scenario.impl.special;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ScenarioFragileRods extends ConfigBasedScenario implements Listener {

    public ScenarioFragileRods(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onUsingRod(ProjectileLaunchEvent e) {
        Projectile entity = e.getEntity();
        ProjectileSource shooter = entity.getShooter();

        if (!(entity instanceof FishHook))
            return;

        if (shooter instanceof Player) {
            PlayerUtils.costPlayerToolDurability((( Player ) shooter));
        }
    }

}
