package org.mcwonderland.uhc.util;

import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.bukkit.World;
import org.mineacademy.fo.Common;

import java.io.File;
import java.io.IOException;

public class ChunkFiller {

    public static void fill(World world) {
        String worldName = world.getName();

        makeFirstRegionFileIfEmpty(world);

        String[] commands = {
                "wb shape square",
                "wb " + worldName + " fill " + Settings.ChunkLoading.FREQUENCY + " " + Settings.ChunkLoading.PADDING + " false",
                "wb " + worldName + " fill confirm"
        };

        Common.log(Messages.Console.CHUNK_LOAD_STARTED.replace("{world}", worldName));

        for (int i = 0; i < commands.length; i++) {
            String command = commands[i];

            Common.runLater(10 * i, () -> {
                Common.dispatchCommand(null, command);
            });
        }
    }

    private static void makeFirstRegionFileIfEmpty(World world) {
        File regionFolder = new File(world.getWorldFolder(), "region");

        if (!regionFolder.exists())
            regionFolder.mkdir();

        if (regionFolder.listFiles().length == 0) {
            File mcaFile = new File(regionFolder, "r.0.0.mca");
            create(mcaFile);
        }
    }

    private static void create(File mcaFile) {
        try {
            mcaFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
