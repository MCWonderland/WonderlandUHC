package org.mcwonderland.uhc.game.settings.sub;

import lombok.Getter;
import lombok.Setter;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

@Getter
@Setter
public class UHCTimerSettings implements ConfigSerializable, org.mcwonderland.uhc.api.game.UHCTimerSettings {
    private Integer pvpTime;
    private Integer damageTime;
    private Integer healTime;
    private Integer borderShrinkTime;
    private Integer disableNetherTime;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Damage_Time", damageTime);
        map.put("Heal_Time", healTime);
        map.put("Pvp_Time", pvpTime);
        map.put("Border_Shrink_Time", borderShrinkTime);
        map.put("Disable_Nether_Time", disableNetherTime);

        return map;
    }

    public static UHCTimerSettings deserialize(SerializedMap map) {
        UHCTimerSettings timer = new UHCTimerSettings();

        timer.damageTime = map.getInteger("Damage_Time", 60 * 5);
        timer.healTime = map.getInteger("Heal_Time", 60 * 10);
        timer.pvpTime = map.getInteger("Pvp_Time", 60 * 20);
        timer.borderShrinkTime = map.getInteger("Border_Shrink_Time", 60 * 35);
        timer.disableNetherTime = map.getInteger("Disable_Nether_Time", 60 * 40);

        return timer;
    }
}
