package org.mcwonderland.uhc.practice;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.model.InventoryContent;
import org.mcwonderland.uhc.model.Teleporter;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Collection;

public class SimplePractice extends AbstractPractice {
    private World world;
    private boolean cleaned = false;

    @Override
    protected Collection<Listener> getListeners() {
        return Lists.newArrayList(
                new CommonPracticeListener(this)
        );
    }

    @Override
    protected void onSetup() {
        Extra.deleteWorld(Settings.Practice.WORLD);

        world = Bukkit.createWorld(new WorldCreator(Settings.Practice.WORLD));

        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(0, 0);
        worldBorder.setSize(Settings.Practice.WORLD_SIZE);
    }

    @Override
    protected void onJoin(Player player) {
        if (!cleaned) {
            cleanWorld();
            cleaned = true;
        }
    }


    private void cleanWorld() {
        Common.logFramed("&e正在優化練習模式的世界...");

        int radius = (Settings.Practice.WORLD_SIZE + 10) / 2;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = 50; y < 200; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    world.loadChunk(block.getChunk());
                    checkBlock(block);
                }
            }
        }

        world.setGameRuleValue("doMobSpawning", "false");
        world.getEntities().stream()
                .filter(entity -> !(entity instanceof Player))
                .forEach(Entity::remove);

        Common.logFramed("&e優化完畢！");
    }

    private void checkBlock(Block block) {
        Material type = block.getType();
        String typeStr = type.toString();

        if (typeStr.contains("WATER") || typeStr.contains("LAVA"))
            block.setType(CompMaterial.STONE.getMaterial());
        else if (CompMaterial.isLog(type) || CompMaterial.isLeaves(type))
            block.setType(CompMaterial.AIR.getMaterial());
        else if (isGrassOrFlower(type))
            block.setType(CompMaterial.AIR.getMaterial());
    }

    private boolean isGrassOrFlower(Material type) {
        return !type.isSolid();
    }

    @Override
    public void stuff(Player player) {
        giveItems(player);
        randomTp(player);
    }

    private void giveItems(Player player) {
        Extra.clearInventory(player);
        InventoryContent content = Game.getSettings().getItemSettings().getPracticeInventoryItems();
        content.setContents(player);
    }

    private void randomTp(Player player) {
        Location location = Teleporter.getRandomTp(world, Settings.Practice.WORLD_SIZE);
        player.teleport(location);
    }

    private ItemStack unBreakItem(ItemStack item) {
        return ItemCreator.of(item)
                .unbreakable(true)
                .make();
    }

}
