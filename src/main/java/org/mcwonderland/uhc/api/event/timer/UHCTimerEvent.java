package org.mcwonderland.uhc.api.event.timer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 2019-12-10 下午 09:03
 */
public class UHCTimerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}