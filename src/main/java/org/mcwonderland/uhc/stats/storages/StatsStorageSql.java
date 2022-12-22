package org.mcwonderland.uhc.stats.storages;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.stats.UHCStats;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.database.SimpleFlatDatabase;


public class StatsStorageSql extends SimpleFlatDatabase<UHCPlayer> implements StatsStorage {

    public StatsStorageSql() {
        connect(Settings.Mysql.IP,
                Settings.Mysql.PORT,
                Settings.Mysql.DATABASE,
                Settings.Mysql.USERNAME,
                Settings.Mysql.PASSWORD,
                Settings.Mysql.TABLE);
    }

    @Override
    protected void onLoad(SerializedMap map, UHCPlayer uhcPlayer) {
        uhcPlayer.setStats(UHCStats.deserialize(map));
    }

    @Override
    protected SerializedMap onSave(UHCPlayer data) {
        return data.getStats().serialize();
    }

    @Override
    public UHCStats loadOrCreate(UHCPlayer uhcPlayer) {
        load(uhcPlayer.getUniqueId(), uhcPlayer);

        return uhcPlayer.getStats();
    }

    @Override
    public void save(UHCPlayer uhcPlayer) {
        save(uhcPlayer.getName(), uhcPlayer.getUniqueId(), uhcPlayer);
    }
}
