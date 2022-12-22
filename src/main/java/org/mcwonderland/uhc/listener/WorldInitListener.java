package org.mcwonderland.uhc.listener;

import de.derfrzocker.custom.ore.generator.api.*;
import org.mcwonderland.uhc.Dependency;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.hook.CustomOreGeneratorHook;
import org.mcwonderland.uhc.populator.OreGen;
import org.mcwonderland.uhc.populator.Populator;
import org.mcwonderland.uhc.util.WorldUtils;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;


public class WorldInitListener implements Listener {

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        World world = event.getWorld();

        if (WorldUtils.isUHCWorld(world) && Dependency.CUSTOM_ORE_GENERATOR.isHooked()) {
            setupCustomOreGenerator(world);
        }
    }

    private void setupCustomOreGenerator(World world) {
        String generator = Game.getSettings().getGenerator();

        resetUhcOreConfigValues(world);

        if (generator.isEmpty())
            return;

        Populator.getPopulator(generator).applyFor(world);
    }


    private void resetUhcOreConfigValues(World world) {
        CustomOreGeneratorService oreService = CustomOreGeneratorHook.getOreService();
        WorldConfig worldConfig = oreService.createWorldConfig(world);

        for (OreConfig oreConfig : worldConfig.getOreConfigs()) {
            OreSettingContainer oreSettings = oreConfig.getOreGeneratorOreSettings();
            OreSettingContainer selectorSettings = oreConfig.getBlockSelectorOreSettings();


            if (oreConfig.getName().startsWith(OreGen.ORE_PREFIX)) {

                for (OreSetting setting : oreSettings.getValues().keySet())
                    oreSettings.removeValue(setting);

                for (OreSetting setting : selectorSettings.getValues().keySet()) {
                    selectorSettings.removeValue(setting);
                }

                oreService.saveOreConfig(oreConfig);
            }
        }
    }
}
