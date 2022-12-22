package org.mcwonderland.uhc.api.event.team;

import lombok.Getter;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;

@Getter
public class TeamCreatedEvent extends TeamPlayerEvent {
    public TeamCreatedEvent(UHCTeam team, UHCPlayer owner) {
        super(team, owner);
    }
}
