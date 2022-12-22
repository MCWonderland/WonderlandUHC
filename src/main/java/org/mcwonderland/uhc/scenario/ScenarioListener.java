package org.mcwonderland.uhc.scenario;

import lombok.val;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.event.timer.UHCStartedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ScenarioListener implements Listener {

    private WonderlandUHC plugin;

    public ScenarioListener(WonderlandUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameStarted(UHCStartedEvent e) {
        reopenScenariosForEventReg();
    }

    private void reopenScenariosForEventReg() {
        val scenarios = plugin.getScenarioManager().getEnabledScenarios();
        scenarios.forEach(scenario -> {
            scenario.disable();
            scenario.enable();
        });
    }

}
