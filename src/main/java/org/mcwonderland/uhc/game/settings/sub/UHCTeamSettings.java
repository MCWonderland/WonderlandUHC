package org.mcwonderland.uhc.game.settings.sub;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.api.enums.TeamSplitMode;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

@Getter
@Setter
public class UHCTeamSettings implements ConfigSerializable, org.mcwonderland.uhc.api.game.UHCTeamSettings {
    private Integer teamSize;
    private Boolean allowTeamFire;
    private TeamSplitMode teamSplitMode;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Team_Size", teamSize);
        map.put("Allow_Team_Fire", allowTeamFire);
        map.put("Team_Split_Mode", teamSplitMode);

        return map;
    }

    public static UHCTeamSettings deserialize(SerializedMap map) {
        UHCTeamSettings uhcTeamSettings = new UHCTeamSettings();

        uhcTeamSettings.teamSize = map.getInteger("Team_Size", 1);
        uhcTeamSettings.allowTeamFire = map.getBoolean("Allow_Team_Fire", false);
        uhcTeamSettings.teamSplitMode = map.get("Team_Split_Mode", TeamSplitMode.class, TeamSplitMode.CHOSEN);

        return uhcTeamSettings;
    }

    @Override
    public Boolean isAllowTeamFire() {
        return getAllowTeamFire();
    }
}
