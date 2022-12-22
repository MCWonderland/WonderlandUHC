package org.mcwonderland.uhc.api.event.team;

import lombok.Getter;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;

@Getter
public class TeamJoinedEvent extends TeamPlayerEvent {

    public TeamJoinedEvent(UHCTeam team, UHCPlayer uhcPlayer) {
        super(team, uhcPlayer);
    }
}
