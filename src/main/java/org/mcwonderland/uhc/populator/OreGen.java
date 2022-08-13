package org.mcwonderland.uhc.populator;

import de.derfrzocker.custom.ore.generator.api.CustomOreGeneratorService;
import de.derfrzocker.custom.ore.generator.api.OreConfig;
import de.derfrzocker.custom.ore.generator.api.OreSetting;
import de.derfrzocker.custom.ore.generator.api.OreSettingContainer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcwonderland.uhc.hook.CustomOreGeneratorHook;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompMaterial;

/**
 * 2019-11-04 下午 07:58
 */


@RequiredArgsConstructor
public class OreGen implements ConfigSerializable {
    public static final String ORE_PREFIX = "Datou_Ore_";

    private final CompMaterial material;
    @Getter
    private final boolean use;
    private final int minY;
    private final int maxY;
    private final int veinsPerChunk;
    private final int maxVeinSize;

    public OreConfig getOreConfig() {
        OreConfig oreConfig = getOreCreateOreConfig();
        applySettings(oreConfig);

        return oreConfig;
    }

    private OreConfig getOreCreateOreConfig() {
        CustomOreGeneratorService oreService = CustomOreGeneratorHook.getOreService();
        String name = ORE_PREFIX + material.name();

        return oreService.getOreConfig(name)
                .orElse(oreService.createOreConfig(
                        name,
                        material.getMaterial(),
                        oreService.getOreGenerator("VANILLA_MINABLE_GENERATOR").get(),
                        oreService.getBlockSelector("COUNT_RANGE").get()));
    }

    private void applySettings(OreConfig oreConfig) {
        oreConfig.addReplaceMaterial(CompMaterial.STONE.getMaterial());

        OreSettingContainer settings = oreConfig.getOreGeneratorOreSettings();
        OreSettingContainer selectorOreSettings = oreConfig.getBlockSelectorOreSettings();

        settings.setValue(OreSetting.getOreSetting("VEIN_SIZE"), Double.valueOf(maxVeinSize));
        selectorOreSettings.setValue(OreSetting.getOreSetting("MINIMUM_HEIGHT"), Double.valueOf(minY));
        selectorOreSettings.setValue(OreSetting.getOreSetting("HEIGHT_RANGE"), Double.valueOf(maxY - minY));
        selectorOreSettings.setValue(OreSetting.getOreSetting("VEINS_PER_CHUNK"), Double.valueOf(veinsPerChunk));
    }

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("Material", material);
        map.put("Use", use);
        map.put("Min_Y", minY);
        map.put("Max_Y", maxY);
        map.put("Veins_Per_Chunk", veinsPerChunk);
        map.put("Max_Vein_Size", maxVeinSize);

        return map;
    }

    public static OreGen deserialize(SerializedMap map) {
        OreGen oreGen = new OreGen(
                map.getMaterial("Material"),
                map.getBoolean("Use"),
                map.getInteger("Min_Y"),
                map.getInteger("Max_Y"),
                map.getInteger("Veins_Per_Chunk"),
                map.getInteger("Max_Vein_Size"));

        return oreGen;
    }
}