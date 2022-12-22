package org.mcwonderland.uhc.listener;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.Scenario;
import org.mcwonderland.uhc.api.event.GameChangeSettingsEvent;
import org.mcwonderland.uhc.api.event.scenario.ScenarioDisabledEvent;
import org.mcwonderland.uhc.api.event.scenario.ScenarioEnabledEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.scenario.ScenarioManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class GameSettingsScenarioListener implements Listener {

    private WonderlandUHC plugin;
    private ScenarioManager scenarioManager;

    public GameSettingsScenarioListener(WonderlandUHC plugin) {
        this.plugin = plugin;
        this.scenarioManager = plugin.getScenarioManager();
    }

    @EventHandler
    public void onScenarioEnabled(ScenarioEnabledEvent e) {
        Game.getSettings().getScenarios().add(e.getScenario().getName());
    }

    @EventHandler
    public void onScenarioDisabled(ScenarioDisabledEvent e) {
        Game.getSettings().getScenarios().remove(e.getScenario().getName());
    }

    @EventHandler
    public void onGameChangeSettings(GameChangeSettingsEvent e) {
        reloadScenarios(e.getNewSettings());
    }

    private void reloadScenarios(UHCGameSettings newSettings) {
        Set<String> scenariosBackup = new HashSet<>(newSettings.getScenarios());

        scenarioManager.getScenarios().forEach(scenarioData -> scenarioData.toggleEnabled(false));

        scenariosBackup.forEach(name -> {
            Scenario scenario = scenarioManager.getScenario(name);
            if (scenario != null)
                scenario.toggleEnabled(true);
        });
    }

}
