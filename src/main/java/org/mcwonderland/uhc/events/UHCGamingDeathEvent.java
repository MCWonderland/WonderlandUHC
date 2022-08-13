package org.mcwonderland.uhc.events;

import org.mcwonderland.uhc.api.event.player.UHCPlayerEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * 2019-12-09 下午 12:35
 */
public class UHCGamingDeathEvent extends UHCPlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final EntityDeathEvent event;

    public UHCGamingDeathEvent(UHCPlayer uhcPlayer, EntityDeathEvent event) {
        super(uhcPlayer);
        this.event = event;
    }

    public LivingEntity getEntity() {
        return event.getEntity();
    }

    public List<ItemStack> getDrops() {
        return event.getDrops();
    }

    public int getDroppedExp() {
        return event.getDroppedExp();
    }

    public void setDroppedExp(int xp) {
        event.setDroppedExp(xp);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
