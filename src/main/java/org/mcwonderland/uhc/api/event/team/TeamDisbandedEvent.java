package org.mcwonderland.uhc.api.event.team;

import org.mcwonderland.uhc.game.UHCTeam;

public class TeamDisbandedEvent extends TeamEvent {

    public TeamDisbandedEvent(UHCTeam team) {
        super(team);
    }
}
