package org.mcwonderland.uhc.events;

import org.bukkit.event.HandlerList;
import org.mcwonderland.uhc.api.event.timer.UHCTimerEvent;
import org.mcwonderland.uhc.game.Game;

public class UHCGameTimerUpdateEvent extends UHCTimerEvent {
    private static final HandlerList handlers = new HandlerList();

    private int currentSecond;

    public UHCGameTimerUpdateEvent(Game game, int currentSecond) {
        super();
        currentSecond = this.currentSecond;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
