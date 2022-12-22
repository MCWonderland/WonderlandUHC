package org.mcwonderland.uhc.game.settings;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.mcwonderland.uhc.game.settings.sub.*;
import org.mcwonderland.uhc.game.settings.sub.*;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UHCGameSettings implements ConfigSerializable, Cloneable, org.mcwonderland.uhc.api.game.UHCGameSettings {

    private String title;
    private int maxPlayers;
    private int appleRate;
    private int initialExperience;
    private boolean whitelistOn;
    private boolean usingNether;
    private boolean enderPearlDamage;
    private String generator;
    private UHCTimerSettings timer;
    private UHCTeamSettings teamSettings;
    private UHCBorderSettings borderSettings;
    private UHCScoreboardSettings scoreboardSettings;
    private UHCItemSettings itemSettings;
    private Set<String> scenarios;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Title", title);
        map.put("Max_Players", maxPlayers);
        map.put("Apple_Rate", appleRate);
        map.put("Initial_Experience", initialExperience);
        map.put("Whitelist_On", whitelistOn);
        map.put("Using_Nether", usingNether);
        map.put("Ender_Pearl_Damage", enderPearlDamage);

        map.put("Generator", generator);
        map.put("Timer", timer.serialize());
        map.put("Team_Settings", teamSettings.serialize());
        map.put("Border_Settings", borderSettings.serialize());
        map.put("Scoreboard_Settings", scoreboardSettings.serialize());
        map.put("Item_Settings", itemSettings.serialize());
        map.put("Scenarios", new ArrayList<>(scenarios));

        return map;
    }

    public static UHCGameSettings deserialize(SerializedMap map) {
        UHCGameSettings settings = new UHCGameSettings();

        settings.title = map.getString("Title", "&a&lWonderland&f&lUHC");
        settings.maxPlayers = map.getInteger("Max_Players", 100);
        settings.appleRate = map.getInteger("Apple_Rate", 2);
        settings.initialExperience = map.getInteger("Initial_Experience", 1);
        settings.whitelistOn = map.getBoolean("Whitelist_On", true);
        settings.usingNether = map.getBoolean("Using_Nether", false);
        settings.enderPearlDamage = map.getBoolean("Ender_Pearl_Damage", false);
        settings.generator = map.getString("Generator", "");
        settings.scenarios = new HashSet<>(map.getStringList("Scenarios"));

        settings.timer = getOrDefault(map, "Timer", UHCTimerSettings.class);
        settings.teamSettings = getOrDefault(map, "Team_Settings", UHCTeamSettings.class);
        settings.borderSettings = getOrDefault(map, "Border_Settings", UHCBorderSettings.class);
        settings.scoreboardSettings = getOrDefault(map, "Scoreboard_Settings", UHCScoreboardSettings.class);
        settings.itemSettings = getOrDefault(map, "Item_Settings", UHCItemSettings.class);

        return settings;
    }

    @SneakyThrows
    public static <E> E getOrDefault(SerializedMap map, String key, Class<E> clazz) {
        Method method = clazz.getDeclaredMethod("deserialize", SerializedMap.class);
        Object defaultValueOfObj = method.invoke(null, new SerializedMap());

        return map.get(key, clazz, ( E ) defaultValueOfObj);
    }

    public static UHCGameSettings defaultSettings() {
        return UHCGameSettings.deserialize(new SerializedMap());
    }

    @Override
    public UHCGameSettings clone() {
        return UHCGameSettings.deserialize(serialize());
    }
}
