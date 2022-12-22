package org.mcwonderland.uhc.api.event.team;

import lombok.Getter;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;

@Getter
public class TeamQuitedEvent extends TeamPlayerEvent {

    public TeamQuitedEvent(UHCTeam team, UHCPlayer uhcPlayer) {
        super(team, uhcPlayer);
    }
}
