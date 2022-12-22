package org.mcwonderland.uhc.game.settings.sub;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.game.border.BorderType;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

@Getter
@Setter
public class UHCBorderSettings implements ConfigSerializable, org.mcwonderland.uhc.api.game.UHCBorderSettings {
    private Integer initialBorder;
    private Integer initialNetherBorder;
    private Integer finalSizeOfShrinkModeBorder;
    private BorderType borderType;
    private Double borderShrinkSpeed;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Initial_Border", initialBorder);
        map.put("Initial_Nether_Border", initialNetherBorder);
        map.put("Final_Size_Of_Shrink_Mode_Border", finalSizeOfShrinkModeBorder);
        map.put("Border_Type", borderType);
        map.put("Border_Shrink_Speed", borderShrinkSpeed);

        return map;
    }

    public static UHCBorderSettings deserialize(SerializedMap map) {
        UHCBorderSettings uhcBorderSettings = new UHCBorderSettings();

        uhcBorderSettings.initialBorder = map.getInteger("Initial_Border", 2000);
        uhcBorderSettings.initialNetherBorder = map.getInteger("Initial_Nether_Border", 1000);
        uhcBorderSettings.finalSizeOfShrinkModeBorder = map.getInteger("Final_Size_Of_Shrink_Mode_Border", 25);
        uhcBorderSettings.borderType = map.get("Border_Type", BorderType.class, BorderType.TIMER);
        uhcBorderSettings.borderShrinkSpeed = map.getDouble("Border_Shrink_Speed", 0.1);

        return uhcBorderSettings;
    }
}
