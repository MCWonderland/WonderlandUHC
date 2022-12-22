package org.mcwonderland.uhc.util.cuboid;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@UtilityClass
public class Cuboid {

    public Stream<Block> getBlocksNearBy(Block center, SelectMode mode) {
        switch (mode) {
            case CONNECT:
                return Stream.of(
                        center.getRelative(BlockFace.NORTH),
                        center.getRelative(BlockFace.SOUTH),
                        center.getRelative(BlockFace.EAST),
                        center.getRelative(BlockFace.WEST),
                        center.getRelative(BlockFace.UP),
                        center.getRelative(BlockFace.DOWN));
            case CUBE: {
                List<Block> blocks = new ArrayList<>();

                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++)
                        for (int y = -1; y <= 1; y++)
                            blocks.add(center.getRelative(x, y, z));

                return blocks.stream();
            }
        }

        return Stream.empty();
    }

    public Set<Location> selectLocations(Location center, int radius) {

        HashSet<Location> locations = new HashSet<>();
        center = new Location(center.getWorld(), center.getBlockX(), center.getBlockY(), center.getBlockZ());

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    locations.add(center.clone().add(x, y, z));
                }
            }
        }

        return locations;
    }
}
