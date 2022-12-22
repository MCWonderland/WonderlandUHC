package org.mcwonderland.uhc.util;

import me.lulu.datounms.DaTouNMS;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Random;

/**
 * 2019-11-04 下午 08:02
 */
public class GenerateUtil {
    public static void createRandomClump(CompMaterial type, Chunk chunk, Random random, int size, int minY, int maxY) {
        int x = chunk.getX() * 16 + Extra.randomNum(16);
        int y = Extra.randomizar(minY, maxY);
        int z = chunk.getZ() * 16 + Extra.randomNum(16);

        createClump(type, new Location(chunk.getWorld(), x, y, z), random, size);
    }

    public static void createClump(CompMaterial type, Location location, Random random, int size) {
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        if (isStoneOrRack(world.getBlockAt(x, y, z))) {
            float f = random.nextFloat() * 3.141593F;

            double d1 = x + 8 + Math.sin(f) * size / 8.0D;
            double d2 = x + 8 - Math.sin(f) * size / 8.0D;
            double d3 = z + 8 + Math.cos(f) * size / 8.0D;
            double d4 = z + 8 - Math.cos(f) * size / 8.0D;

            double d5 = y + random.nextInt(3) - 2;
            double d6 = y + random.nextInt(3) - 2;

            for (int i = 0; i <= size; i++) {
                double d7 = d1 + (d2 - d1) * i / size;
                double d8 = d5 + (d6 - d5) * i / size;
                double d9 = d3 + (d4 - d3) * i / size;

                double d10 = random.nextDouble() * size / 16.0D;
                double d11 = (Math.sin(i * 3.141593F / size) + 1.0D) * d10 + 1.0D;
                double d12 = (Math.sin(i * 3.141593F / size) + 1.0D) * d10 + 1.0D;

                int j = (int) Math.floor(d7 - d11 / 2.0D);
                int k = (int) Math.floor(d8 - d12 / 2.0D);
                int m = (int) Math.floor(d9 - d11 / 2.0D);

                int n = (int) Math.floor(d7 + d11 / 2.0D);
                int i1 = (int) Math.floor(d8 + d12 / 2.0D);
                int i2 = (int) Math.floor(d9 + d11 / 2.0D);

                for (int i3 = j; i3 <= n; i3++) {
                    double d13 = (i3 + 0.5D - d7) / (d11 / 2.0D);
                    if (d13 * d13 < 1.0D) {
                        for (int i4 = k; i4 <= i1; i4++) {
                            double d14 = (i4 + 0.5D - d8) / (d12 / 2.0D);
                            if (d13 * d13 + d14 * d14 < 1.0D) {
                                for (int i5 = m; i5 <= i2; i5++) {
                                    double d15 = (i5 + 0.5D - d9) / (d11 / 2.0D);
                                    if ((d13 * d13 + d14 * d14 + d15 * d15 < 1.0D) && isStoneOrRack(world.getBlockAt(i3, i4, i5))) {
                                        DaTouNMS.getWorldNMS().setBlockSuperFast(world, i3, i4, i5, type.getMaterial(), (byte) type.getData(), false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isStoneOrRack(Block b) {
        return b.getType() == CompMaterial.STONE.getMaterial()
                || b.getType() == CompMaterial.NETHERRACK.getMaterial();
    }
}