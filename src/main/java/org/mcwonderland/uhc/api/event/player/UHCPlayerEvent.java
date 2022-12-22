package org.mcwonderland.uhc.api.event.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
@Getter
public class UHCPlayerEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    private final UHCPlayer uhcPlayer;


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
