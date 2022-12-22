package org.mcwonderland.uhc.game.settings;

import org.mcwonderland.uhc.settings.UHCFiles;
import org.bukkit.entity.Player;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.*;

public class UHCGameSettingsSaver extends YamlConfig {
    private static UHCGameSettingsSaver saver = new UHCGameSettingsSaver();
    private static final Map<UUID, List<UHCGameSettings>> savedSettings = new HashMap<>();

    private UHCGameSettingsSaver() {
        loadConfiguration(UHCFiles.SAVED_GAMES);
    }

    public static void reloadFromFile() {
        savedSettings.clear();
        saver = new UHCGameSettingsSaver();
    }

    public static List<UHCGameSettings> getSavedSettings(Player player) {
        List<UHCGameSettings> settings = savedSettings.get(player.getUniqueId());

        if (settings == null)
            settings = saver.loadPlayerSavedGames(player);

        return settings;
    }

    public static void saveGameSettings(Player player) {
        List<UHCGameSettings> settings = savedSettings.get(player.getUniqueId());

        if (settings != null)
            saver.saveGameSettings(player.getUniqueId().toString(), settings);
    }

    private List<UHCGameSettings> loadPlayerSavedGames(Player player) {
        String path = player.getUniqueId().toString();
        List<UHCGameSettings> settings = isSet(path) ? getList(path, UHCGameSettings.class) : new ArrayList<>();

        savedSettings.put(player.getUniqueId(), settings);

        return settings;
    }

    private void saveGameSettings(String path, Collection<UHCGameSettings> settings) {
        set(path, settings);
        save();
    }
}
