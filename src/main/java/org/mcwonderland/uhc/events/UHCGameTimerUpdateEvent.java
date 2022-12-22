package org.mcwonderland.uhc.events;

import org.bukkit.event.HandlerList;
import org.mcwonderland.uhc.api.event.timer.UHCTimerEvent;

public class UHCGameTimerUpdateEvent extends UHCTimerEvent {
    private static final HandlerList handlers = new HandlerList();

    private int currentSecond;

    public UHCGameTimerUpdateEvent(int currentSecond) {
        super();
        this.currentSecond = currentSecond;
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
