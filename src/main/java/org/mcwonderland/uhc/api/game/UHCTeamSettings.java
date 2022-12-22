package org.mcwonderland.uhc.api.game;

import org.mcwonderland.uhc.api.enums.TeamSplitMode;

public interface UHCTeamSettings {
    Boolean isAllowTeamFire();

    void setTeamSize(Integer teamSize);

    void setAllowTeamFire(Boolean allowTeamFire);

    void setTeamSplitMode(TeamSplitMode teamSplitMode);

    Integer getTeamSize();

    Boolean getAllowTeamFire();

    TeamSplitMode getTeamSplitMode();
}
