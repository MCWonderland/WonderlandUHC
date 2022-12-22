package org.mcwonderland.uhc.api.event.team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcwonderland.uhc.game.UHCTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    @Getter
    private final UHCTeam team;

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
