package org.mcwonderland.uhc.game.settings;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.settings.YamlConfig;

import java.io.File;

public class CacheSaver extends YamlConfig {
    private static final CacheSaver saver = new CacheSaver();
    @Getter
    @Setter
    private static UHCGameSettings settings;
    @Getter
    @Setter
    private static LoadingStatus loadingStatus;
    @Getter
    @Setter
    private static String host;

    public CacheSaver() {
        loadConfiguration(null, UHCFiles.CACHE);

        loadingStatus = get("Loading_Status", LoadingStatus.class, LoadingStatus.CONFIGURING);
        host = getString("Host", "");
        settings = get("Settings", UHCGameSettings.class);
    }

    public static void saveCache() {
        saver.saveCache(Game.getSettings());
    }

    public static void deleteCache() {
        File file = FileUtil.getFile(saver.getFileName());

        if (file.exists()) {
            YamlConfig.clearLoadedSections();
            file.delete();
        }
    }

    private void saveCache(UHCGameSettings settings) {
        set("Host", host);
        set("Loading_Status", loadingStatus);
        set("Settings", settings);
        save();
    }
}
