package org.mcwonderland.uhc.api.event.player;

import lombok.Getter;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * 2019-12-09 下午 12:35
 */
@Getter
public class UHCPlayerDamageEvent extends UHCPlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final EntityDamageEvent event;

    public UHCPlayerDamageEvent(UHCPlayer uhcPlayer, EntityDamageEvent event) {
        super(uhcPlayer);
        this.event = event;
    }

    @Override
    public boolean isCancelled() {
        return event.isCancelled();
    }

    @Override
    public void setCancelled(boolean b) {
        event.setCancelled(b);
    }

    public double getDamage() {
        return event.getDamage();
    }

    public void setDamage(double damage) {
        event.setDamage(damage);
    }

    public EntityDamageEvent.DamageCause getCause() {
        return event.getCause();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}