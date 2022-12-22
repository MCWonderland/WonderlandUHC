package org.mcwonderland.uhc.stats.storages;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.stats.UHCStats;

public interface StatsStorage {

    UHCStats loadOrCreate(UHCPlayer uhcPlayer);

    void save(UHCPlayer uhcPlayer);
}
