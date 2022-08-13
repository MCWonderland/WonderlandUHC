package org.mcwonderland.uhc.api.event.scenario;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.Scenario;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ScenarioInitEvent extends Event {

    private static HandlerList handlerList = new HandlerList();


    public void registerScenario(Scenario scenario) {
        WonderlandUHC.getInstance().getScenarioManager().register(scenario);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
