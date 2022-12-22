package org.mcwonderland.uhc.api.event.player;

import org.mcwonderland.uhc.game.player.UHCPlayer;

public class UHCPlayerRespawnedEvent extends UHCPlayerEvent {

    public UHCPlayerRespawnedEvent(UHCPlayer uhcPlayer) {
        super(uhcPlayer);
    }
}
