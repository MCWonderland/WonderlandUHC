package org.mcwonderland.uhc.scenario.impl.disable;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

/**
 * 2019-12-07 下午 02:55
 */
public class ScenarioHorseLess extends ConfigBasedScenario implements Listener {

    public ScenarioHorseLess(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onRidingHorse(VehicleEnterEvent e) {
        if (isPlayerTryToRideHorse(e))
            e.setCancelled(true);
    }

    private boolean isPlayerTryToRideHorse(VehicleEnterEvent e) {
        return e.getVehicle() instanceof Horse &&
                e.getEntered() instanceof Player;
    }
}
