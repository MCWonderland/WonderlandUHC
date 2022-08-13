package org.mcwonderland.uhc.settings;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UHCFiles {

    @Getter
    private static final List<String> fileNames = new ArrayList<>();

    public static final String BIOMES = addSettingsFile("biomes.yml");
    public static final String BROADCAST = addSettingsFile("broadcasts.yml");
    public static final String COMMANDS = addSettingsFile("commands.yml");
    public static final String ITEMS = addSettingsFile("items.yml");
    public static final String MENUS = addSettingsFile("gui.yml");
    public static final String MESSAGES = addSettingsFile("messages.yml");
    public static final String POPULATORS = addSettingsFile("populators.yml");
    public static final String SCENARIOS = addSettingsFile("scenarios.yml");
    public static final String SCOREBOARDS = addSettingsFile("scoreboards.yml");
    public static final String SETTINGS = addSettingsFile("settings.yml");
    public static final String SPAWNS = addSettingsFile("spawns.yml");
    public static final String STATS = addSettingsFile("stats.yml");

    public static final String PERMISSIONS = addSettingsFile("permissions.txt");
    public static final String CACHE = addSettingsFile("cache.db");
    public static final String SAVED_GAMES = addSettingsFile("savedgames.db");
    public static final String SOUNDS = addSettingsFile("sounds.yml");

    private static String addSettingsFile(String name) {
        fileNames.add(name);
        return name;
    }
}
