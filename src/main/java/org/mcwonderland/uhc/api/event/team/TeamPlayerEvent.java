package org.mcwonderland.uhc.api.event.team;

import lombok.Getter;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;

public class TeamPlayerEvent extends TeamEvent {

    @Getter
    private UHCPlayer player;

    protected TeamPlayerEvent(UHCTeam team, UHCPlayer player) {
        super(team);

        this.player = player;
    }
}
