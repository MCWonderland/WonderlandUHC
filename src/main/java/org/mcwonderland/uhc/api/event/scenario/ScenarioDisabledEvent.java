package org.mcwonderland.uhc.api.event.scenario;

import lombok.Getter;
import org.mcwonderland.uhc.api.Scenario;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ScenarioDisabledEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    @Getter
    private Scenario scenario;

    public ScenarioDisabledEvent(Scenario scenario) {
        this.scenario = scenario;
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
