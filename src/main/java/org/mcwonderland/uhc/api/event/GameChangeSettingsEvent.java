package org.mcwonderland.uhc.api.event;

import lombok.Getter;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameChangeSettingsEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    @Getter
    private UHCGameSettings newSettings;

    public GameChangeSettingsEvent(UHCGameSettings newSettings) {
        this.newSettings = newSettings;
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
