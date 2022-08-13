package org.mcwonderland.uhc.model;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.mcwonderland.uhc.util.UniqueQueue;
import org.mcwonderland.uhc.util.cuboid.Cuboid;
import org.mcwonderland.uhc.util.cuboid.SelectMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class VeinMiner {
    private static final int MAX_COUNT = 100;

    private Set<UUID> mining = new HashSet<>();

    public void mineVeins(Block block, Player player, SelectMode mode) {
        if (isMining(player))
            return;

        mining.add(player.getUniqueId());

        Set<Block> blocks = calculateConnectBlocks(block, mode);
        blocks.forEach(b -> PlayerUtils.breakBlockNms(player, b));

        mining.remove(player.getUniqueId());
    }

    private Set<Block> calculateConnectBlocks(Block startBlock, SelectMode mode) {
        Material type = getType(startBlock);
        Set<Block> allBlocks = new HashSet<>();
        UniqueQueue<Block> temp = new UniqueQueue<>();
        temp.addAll(getNearBlocks(startBlock, mode));

        int count = 0;

        while (!temp.isEmpty() && count < MAX_COUNT) {
            Block b = temp.remove();

            if (getType(b) == type && allBlocks.add(b)) {
                temp.addAll(getNearBlocks(b, mode));
                count++;
            }
        }

        return allBlocks;
    }

    private Set<Block> getNearBlocks(Block block, SelectMode mode) {
        return Cuboid.getBlocksNearBy(block, mode)
                .filter(b -> getType(b) == getType(block))
                .collect(Collectors.toSet());
    }

    private Material getType(Block block) {
        return block.getType().toString().equalsIgnoreCase("GLOWING_REDSTONE_ORE") ?
                Material.REDSTONE_ORE : block.getType();
    }

    private boolean isMining(Player player) {
        return mining.contains(player.getUniqueId());
    }
}
