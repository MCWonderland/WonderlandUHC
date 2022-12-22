package org.mcwonderland.uhc.populator;

import com.google.common.collect.Lists;
import de.derfrzocker.custom.ore.generator.api.CustomOreGeneratorService;
import de.derfrzocker.custom.ore.generator.api.OreConfig;
import de.derfrzocker.custom.ore.generator.api.WorldConfig;
import lombok.AccessLevel;
import lombok.Getter;
import org.mcwonderland.uhc.Dependency;
import org.mcwonderland.uhc.hook.CustomOreGeneratorHook;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.bukkit.World;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.BoxedMessage;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 2019-07-03 下午 06:25
 */
@Getter
public class Populator extends YamlConfig implements ConfigSerializable {
    private static final List<Populator> populators = new ArrayList<>();

    public static void loadPopulators() {
        new PopulatorLoader().load();
    }

    public static Populator getPopulator(String name) {
        for (Populator populator : populators)
            if (populator.getPathPrefix().equalsIgnoreCase(name))
                return populator;

        BoxedMessage.broadcast("&cCan't find any populator that named '" + name + "', using default one");
        return defaultPopulator();
    }

    public static String getPopulatorName(String name) {
        if (!Dependency.CUSTOM_ORE_GENERATOR.isHooked() || name.isEmpty())
            return "null";

        return getPopulator(name).getFancyName();
    }

    public static Populator defaultPopulator() {
        try {
            return populators.get(0);
        } catch (IndexOutOfBoundsException e) {
            return Populator.deserialize(new SerializedMap());
        }
    }

    public static Collection<Populator> getPopulators() {
        return Lists.newArrayList(populators);
    }

    private String fancyName;
    private List<String> description;
    private CompMaterial icon;
    @Getter(AccessLevel.PRIVATE)
    private List<OreGen> oreGens;

    private Populator(String path) {
        setPathPrefix(path);
        loadConfiguration(UHCFiles.POPULATORS);
    }

    public void applyFor(World world) {
        CustomOreGeneratorService oreService = CustomOreGeneratorHook.getOreService();
        WorldConfig worldConfig = oreService.createWorldConfig(world);

        for (OreGen oreGen : oreGens) {
            if (oreGen.isUse()) {
                OreConfig oreConfig = oreGen.getOreConfig();
                addOreConfig(worldConfig, oreConfig);
                oreService.saveOreConfig(oreConfig);
            }
        }

        oreService.saveWorldConfig(worldConfig);
    }

    private void addOreConfig(WorldConfig worldConfig, OreConfig oreConfig) {
        if (!worldConfig.getAllOreConfigs().contains(oreConfig.getName()))
            worldConfig.addOreConfig(oreConfig);
    }

    public String getKeyName() {
        return getPathPrefix();
    }

    @Override
    public SerializedMap saveToMap() {
        SerializedMap map = new SerializedMap();

        map.put("Key", getKeyName());
        map.put("Icon", icon);
        map.put("Fancy_Name", fancyName);
        map.put("Description", description);
        map.put("Ores", oreGens);

        return map;
    }

    public static Populator deserialize(SerializedMap map) {
        String key = map.getString("Key", "Default");
        Populator populator = new Populator(key);

        populator.icon = map.getMaterial("Icon", CompMaterial.STONE);
        populator.fancyName = map.getString("Fancy_Name", key);
        populator.description = map.getList("Description", String.class);
        populator.oreGens = map.getList("Ores", OreGen.class);

        return populator;
    }

    private static class PopulatorLoader extends YamlConfig {
        public void load() {
            loadConfiguration(UHCFiles.POPULATORS);

            populators.clear();

            for (String key : getKeys(false)) {
                SerializedMap map = getMap(key);
                map.put("Key", key);

                populators.add(Populator.deserialize(map));
            }
        }
    }
}