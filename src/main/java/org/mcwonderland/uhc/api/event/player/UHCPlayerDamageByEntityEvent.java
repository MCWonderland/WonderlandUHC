package org.mcwonderland.uhc.api.event.player;

import lombok.Getter;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 2019-12-09 下午......................................... 12:35
 */
@Getter
public class UHCPlayerDamageByEntityEvent extends UHCPlayerDamageEvent {
    private final EntityDamageByEntityEvent event;

    public UHCPlayerDamageByEntityEvent(UHCPlayer uhcPlayer, EntityDamageByEntityEvent event) {
        super(uhcPlayer, event);
        this.event = event;
    }

    public Entity getDamager() {
        return event.getDamager();
    }
}