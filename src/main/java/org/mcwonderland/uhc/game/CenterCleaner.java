package org.mcwonderland.uhc.game;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.populator.Populator;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.MinecraftVersion;

import javax.annotation.Nullable;

public class CenterCleaner {
    public static void createWorld(String worldName, Player p, @Nullable String seed) {
        int range = Settings.CenterCleaner.RANGE;
        int riverRange = Settings.CenterCleaner.CHECK_RIVER_IN;
        boolean allowBadBiomes = Settings.CenterCleaner.ALLOW_BAD_BIOME;
        String generatorSettings = Settings.CenterCleaner.GENERATOR_SETTINGS;

        new BukkitRunnable() {

            World uhcWorld = null;
            Boolean bad = false;
            Integer limited = 0;
            Integer high = 0;


            @Override
            public void run() {
                if (Bukkit.getWorlds().contains(Bukkit.getWorld(worldName))) {
                    p.teleport(UHCWorldUtils.getLobbySpawn());
                    Bukkit.unloadWorld(worldName, false);
                    Chat.send(p, Messages.Host.WORLD_DELETED);
                }

                Extra.deleteWorld(worldName);

                WorldCreator creator = new WorldCreator(worldName);

                if (seed != null) {
                    try {
                        creator.seed(Long.parseLong(seed));
                    } catch (Exception ex) {
                        creator.seed(seed.hashCode());
                    }
                }

                if (!generatorSettings.isEmpty())
                    creator.generatorSettings(generatorSettings);

                uhcWorld = creator.createWorld();
                if (Game.getGame().isCenterCleaner()) {
                    limited = 0;
                    high = 0;
                    bad = false;

                    for (int x = -range; x < range; x++) {
                        for (int z = -range; z < range; z++) {
                            Biome b = uhcWorld.getBiome(x, z);

                            if (isInRiverRange(x, z) && (b == Biome.RIVER || b == Biome.FROZEN_RIVER)) {
                                Chat.broadcast(Messages.CenterCleaner.RIVER_CENTER);
                                return;
                            }

                            BIOME_THRESHOLD t = isValidBiome(b, x, z);

                            if (t == BIOME_THRESHOLD.LIMITED)
                                limited++;
                            if (t == BIOME_THRESHOLD.DISALLOWED)
                                bad = true;

                            if (checkBad())
                                return;
                        }
                    }

                    for (int x = -range; x <= range; ++x) {
                        for (int z = -range; z <= range; z++) {

                            if (uhcWorld.getHighestBlockYAt(x, z) > Settings.CenterCleaner.MAX_HIGH)
                                high = uhcWorld.getHighestBlockYAt(x, z);

                            if (high >= Settings.CenterCleaner.MAX_HIGH) {
                                Chat.broadcast(Messages.CenterCleaner.TOO_HIGH
                                        .replace("{height}", high + ""));
                                return;
                            }
                        }
                    }
                }

                Chat.send(p, Messages.Host.WORLD_CREATED.replace("{generator}", Populator.getPopulatorName(Game.getSettings().getGenerator())));
                p.teleport(UHCWorldUtils.getZeroZero());
                p.setGameMode(GameMode.CREATIVE);
                Extra.sound(p, Sounds.Host.WORLD_CREATED);
                cancel();
            }

            private boolean isInRiverRange(int x, int z) {
                return Math.abs(x) >= riverRange || Math.abs(z) >= riverRange;
            }

            private boolean checkBad() {

                if (allowBadBiomes && (limited >= Settings.CenterCleaner.BAD_BIOME_LIMIT || bad == true)) {
                    Chat.broadcast(Messages.CenterCleaner.BAD_BIOME
                            .replace("{amount}", limited + ""));
                    return true;
                }

                return false;
            }

        }.runTaskTimer(WonderlandUHC.getInstance(), 0L, 5L);

    }

    private static BIOME_THRESHOLD isValidBiome(Biome biome, int i, int j) {
        if (MinecraftVersion.olderThan(MinecraftVersion.V.v1_9)) {
            return isValidBiome1_8(biome, i, j);
        } else {
            return isValidBiome1_9(biome, i, j);
        }
    }

    private static BIOME_THRESHOLD isValidBiome1_8(Biome biome, int i, int j) {
        // flag = is this in 100x100
        final boolean flag = i <= 100 && i >= -100 && j <= 100 && j >= -100;
        if (biome == Biome.DESERT || biome == Biome.valueOf("DESERT_HILLS") || biome == Biome.valueOf("DESERT_MOUNTAINS")
                || biome == Biome.valueOf("PLAINS") || biome == Biome.valueOf("SUNFLOWER_PLAINS") || biome == Biome.valueOf("SWAMPLAND")
                || biome == Biome.valueOf("SWAMPLAND_MOUNTAINS") || biome == Biome.valueOf("SMALL_MOUNTAINS") || biome == Biome.SAVANNA
                || biome == Biome.valueOf("SAVANNA_MOUNTAINS") || biome == Biome.valueOf("SAVANNA_PLATEAU")
                || biome == Biome.valueOf("SAVANNA_PLATEAU_MOUNTAINS") || biome == Biome.valueOf("ICE_PLAINS")) {
            return BIOME_THRESHOLD.ALLOWED;
        } else if (flag && (biome == Biome.valueOf("FOREST") || biome == Biome.valueOf("RIVER") || biome == Biome.valueOf("FROZEN_RIVER")
                || biome == Biome.valueOf("FOREST_HILLS") | biome == Biome.valueOf("BIRCH_FOREST") || biome == Biome.valueOf("BIRCH_FOREST_HILLS")
                || biome == Biome.valueOf("BIRCH_FOREST_HILLS_MOUNTAINS") || biome == Biome.valueOf("BIRCH_FOREST_MOUNTAINS")
                || biome == Biome.valueOf("TAIGA_HILLS") || biome == Biome.valueOf("TAIGA_MOUNTAINS") || biome == Biome.valueOf("ICE_PLAINS_SPIKES")
                || biome == Biome.valueOf("MEGA_SPRUCE_TAIGA") || biome == Biome.valueOf("MEGA_SPRUCE_TAIGA_HILLS")
                || biome == Biome.valueOf("MEGA_TAIGA") || biome == Biome.valueOf("MEGA_TAIGA_HILLS") || biome == Biome.valueOf("FLOWER_FOREST")
                || biome == Biome.valueOf("COLD_BEACH") || biome == Biome.valueOf("COLD_TAIGA_HILLS")
                || biome == Biome.valueOf("COLD_TAIGA_MOUNTAINS"))) {
            return BIOME_THRESHOLD.LIMITED;
        } else if (flag && (biome == Biome.valueOf("ROOFED_FOREST") || biome == Biome.valueOf("ROOFED_FOREST_MOUNTAINS")
                || biome == Biome.valueOf("MESA") || biome == Biome.valueOf("MESA_PLATEAU") || biome == Biome.valueOf("MESA_BRYCE")
                || biome == Biome.valueOf("MESA_PLATEAU_FOREST") || biome == Biome.valueOf("MESA_PLATEAU_FOREST_MOUNTAINS")
                || biome == Biome.valueOf("MESA_PLATEAU_MOUNTAINS") || biome == Biome.valueOf("EXTREME_HILLS") || biome == Biome.valueOf("COLD_TAIGA")
                || biome == Biome.valueOf("EXTREME_HILLS_MOUNTAINS") || biome == Biome.valueOf("TAIGA") || biome == Biome.valueOf("EXTREME_HILLS_PLUS")
                || biome == Biome.valueOf("EXTREME_HILLS_PLUS_MOUNTAINS") || biome == Biome.valueOf("FROZEN_OCEAN")
                || biome == Biome.valueOf("COLD_TAIGA_HILLS") || biome == Biome.valueOf("ICE_MOUNTAINS"))) {
            return BIOME_THRESHOLD.DISALLOWED;
        }

        // We are only picky at 0,0 otherwise stuff is allowed
        return flag ? BIOME_THRESHOLD.DISALLOWED : BIOME_THRESHOLD.ALLOWED;
    }


    private static BIOME_THRESHOLD isValidBiome1_9(Biome biome, int i, int j) {
        // flag = is this in 100x100
        final boolean flag = i <= 100 && i >= -100 && j <= 100 && j >= -100;
        String biomeName = biome.name();
        if (biomeName.equals("DESERT") || biomeName.equals("DESERT_HILLS") || biomeName.equals("MUTATED_DESERT")
                || biomeName.equals("PLAINS") || biomeName.equals("MUTATED_PLAINS") || biomeName.equals("SWAMPLAND")
                || biomeName.equals("MUTATED_SWAMPLAND") || biomeName.equals("SAVANNA")
                || biomeName.equals("MUTATED_SAVANNA") || biomeName.equals("SAVANNA_ROCK")
                || biomeName.equals("SAVANNA_ROCK") || biomeName.equals("ICE_FLATS")) {
            return BIOME_THRESHOLD.ALLOWED;
        } else if (flag && (biomeName.equals("FOREST") || biomeName.equals("RIVER") || biomeName.equals("FROZEN_RIVER")
                || biomeName.equals("FOREST_HILLS") || biomeName.equals("BIRCH_FOREST") || biomeName.equals("BIRCH_FOREST_HILLS")
                || biomeName.equals("MUTATED_BIRCH_FOREST_HILLS")
                || biomeName.equals("MUTATED_BIRCH_FOREST") || biomeName.equals("TAIGA_HILLS")
                || biomeName.equals("MUTATED_TAIGA") || biomeName.equals("ICE_FLATS")
                || biomeName.equals("MUTATED_REDWOOD_TAIGA")
                || biomeName.equals("MUTATED_REDWOOD_TAIGA_HILLS") || biomeName.equals("REDWOOD_TAIGA")
                || biomeName.equals("TAIGA_COLD_HILLS") || biomeName.equals("COLD_BEACH")
                || biomeName.equals("TAIGA_COLD_HILLS") || biomeName.equals("MUTATED_TAIGA_COLD"))) {
            return BIOME_THRESHOLD.LIMITED;
        } else if (flag && (biomeName.equals("ROOFED_FOREST") || biomeName.equals("MUTATED_ROOFED_FOREST")
                || biomeName.equals("MESA") || biomeName.equals("MESA_ROCK")
                || biomeName.equals("MUTATED_SAVANNA") || biomeName.equals("MUTATED_MESA")
                || biomeName.equals("EXTREME_HILLS") || biomeName.equals("TAIGA_COLD")
                || biomeName.equals("EXTREME_HILLS_WITH_TREES") || biomeName.equals("TAIGA")
                || biomeName.equals("EXTREME_HILLS") || biomeName.equals("MUTATED_EXTREME_HILLS")
                || biomeName.equals("FROZEN_OCEAN") || biomeName.equals("TAIGA_COLD_HILLS")
                || biomeName.equals("ICE_MOUNTAINS"))) {
            return BIOME_THRESHOLD.DISALLOWED;
        }

        // We are only picky at 0,0 otherwise stuff is allowed
        return flag ? BIOME_THRESHOLD.DISALLOWED : BIOME_THRESHOLD.ALLOWED;
    }

    public enum BIOME_THRESHOLD {
        DISALLOWED, LIMITED, ALLOWED
    }
}
