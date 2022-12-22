package org.mcwonderland.uhc.game.settings.sub;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompChatColor;

@Getter
@Setter
public class UHCScoreboardSettings implements ConfigSerializable {
    private SidebarTheme sidebarTheme;
    private Integer scoreboardUpdateTick;
    private CompChatColor heartColor;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Theme", sidebarTheme.getName());
        map.put("Update_Tick", scoreboardUpdateTick);
        map.put("Heart_Color", heartColor);

        return map;
    }

    public static UHCScoreboardSettings deserialize(SerializedMap map) {
        UHCScoreboardSettings settings = new UHCScoreboardSettings();

        settings.sidebarTheme = SidebarTheme.getThemeOrDefault(map.getString("Theme", ""));
        settings.scoreboardUpdateTick = map.getInteger("Update_Tick", 5);
        settings.heartColor = CompChatColor.of("&c");//map.getString("Heart_Color", "&c"));

        return settings;
    }
}
