package org.mcwonderland.uhc.stats.storages;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mcwonderland.uhc.stats.UHCStats;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.settings.YamlConfig;


public class StatsStorageYaml extends YamlConfig implements StatsStorage {

    public StatsStorageYaml() {
        loadConfiguration(UHCFiles.STATS);
    }

    @Override
    public UHCStats loadOrCreate(UHCPlayer uhcPlayer) {
        String key = uhcPlayer.getUniqueId().toString();
        SerializedMap map = isSet(key) ? getMap(key) : new SerializedMap();

        UHCStats uhcStats = UHCStats.deserialize(map);

        return uhcStats;
    }

    @Override
    public void save(UHCPlayer uhcPlayer) {
        set(uhcPlayer.getUniqueId().toString(), uhcPlayer.getStats());
    }
}
