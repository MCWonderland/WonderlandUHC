package org.mcwonderland.uhc.listener;

import com.wimbli.WorldBorder.Events.WorldBorderFillFinishedEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.game.settings.sub.UHCBorderSettings;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.*;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mcwonderland.uhc.util.*;
import org.mineacademy.fo.Common;

public class WorldFillListener implements Listener {

    @EventHandler
    public void worldFinish(WorldBorderFillFinishedEvent e) {
        World world = e.getWorld();

        if (!WorldUtils.isUHCWorld(world))
            return;

        Common.logNoPrefix(Messages.Console.CHUNK_LOAD_FINISHED
                .replace("{world}", world.getName())
                .replace("{number}", "" + e.getTotalChunks())
        );

        UHCBorderSettings borderSettings = Game.getSettings().getBorderSettings();

        if (world == UHCWorldUtils.getWorld())
            buildBorders(world, borderSettings.getInitialBorder());
        else if (world == UHCWorldUtils.getNether())
            buildBorders(world, borderSettings.getInitialNetherBorder());
    }

    private void buildBorders(World world, int size) {
        Common.logNoPrefix("&eGenerating Border...");

        generateBorder(world, size);

        Common.runLater(20 * 3, () -> {
            for (int i = 0; i < Settings.Border.BEDROCK_BORDER_HEIGHT - 1; i++)
                generateBorder(world, size);

            Common.logNoPrefix("&eBorder Generated!");
            onBorderGenerated(world);
        });
    }

    private void generateBorder(World world, int size) {
        if (Settings.Border.BEDROCK_BORDER_HEIGHT > 0)
            BorderUtil.generateBorder(world, size);
    }

    private void onBorderGenerated(World world) {
        if (world == UHCWorldUtils.getWorld())
            checkNether();
        else if (world == UHCWorldUtils.getNether())
            saveAndRestart();
    }

    private void checkNether() {
        if (Game.getSettings().isUsingNether()) {
            Common.logNoPrefix(Messages.Console.CHUNK_LOAD_NETHER_DETECTED);
            startLoadingNetherChunks();
        } else
            checkForceChunk();

    }

    private void checkForceChunk() {
        if (Settings.ChunkLoading.FORCE_LOADING_NETHER_CHUNK) {
            Common.logNoPrefix(Messages.Console.FORCE_NETHER_CHUNK_ON);
            startLoadingNetherChunks();
        } else
            saveAndRestart();
    }

    private void startLoadingNetherChunks() {
        ChunkFiller.fill(UHCWorldUtils.getNether());
    }

    private void saveAndRestart() {
        CacheSaver.setLoadingStatus(LoadingStatus.DONE);
        CacheSaver.saveCache();
        Extra.restartServer();
    }
}

