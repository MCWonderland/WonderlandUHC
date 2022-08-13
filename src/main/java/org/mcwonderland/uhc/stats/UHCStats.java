package org.mcwonderland.uhc.stats;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Material;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

public class UHCStats implements ConfigSerializable {

    @Getter(AccessLevel.PRIVATE)
    public Map<Material, Integer> oreMined = new HashMap<>();
    public int gamePlayed;
    public int totalKills;
    public int totalWins;
    public int kills;


    public double getKdr() {
        return totalKills / Math.max(gamePlayed, 1);
    }

    public int addOreMined(Material material) {
        Integer amount = oreMined.getOrDefault(material, 0);
        oreMined.put(material, ++amount);

        return amount;
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Game_Played", gamePlayed);
        map.put("Kills", totalKills);
        map.put("Wins", totalWins);

        return map;
    }

    public static UHCStats deserialize(SerializedMap map) {
        UHCStats uhcStats = new UHCStats();

        uhcStats.gamePlayed = map.getInteger("Game_Played", 0);
        uhcStats.totalKills = map.getInteger("Kills", 0);
        uhcStats.totalWins = map.getInteger("Wins", 0);

        return uhcStats;
    }
}
