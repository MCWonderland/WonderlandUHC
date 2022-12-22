package org.mcwonderland.uhc.scenario.impl.death;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lulu.datounms.model.NewerSpigotAPI;
import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 2019-12-07 下午 03:12
 */
@Getter
public class ScenarioTimeBomb extends ConfigBasedScenario implements Listener {
    private static final Set<TimeBombChest> timeBombs = new HashSet<>();

    @FilePath(name = "Exploded")
    private String explodedMessage;
    @FilePath(name = "Explode_Blocks")
    private Boolean explodedBlocks;
    @FilePath(name = "Explosion_Power")
    private Integer explosionPower;
    @FilePath(name = "Explode_After_Seconds")
    private Integer explodeAfterSeconds;
    @FilePath(name = "Warn_Sound")
    private SimpleSound warnSound;
    @FilePath(name = "Warn_Sound_Danger")
    private SimpleSound warnSoundDanger;


    public ScenarioTimeBomb(ScenarioName name) {
        super(name);

    }

    @Override
    protected void onConfigReload() {
        Common.runTimer(20, new ChestExplodeTicker());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    protected void handleTimeBomb(UHCGamingDeathEvent e) {
        new TimeBombRunner(e).run();
        e.getDrops().clear();
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replaceTime(explodeAfterSeconds);
    }

    class TimeBombRunner {
        private final Entity entity;
        private Block leftSideChest, RightSideChest;
        private final List<ItemStack> drops;

        private TimeBombRunner(UHCGamingDeathEvent event) {
            this.entity = event.getEntity();
            this.drops = event.getDrops();
        }

        private void run() {
            spawnLargeChest();
            clearUpperBlocks();
            putItemsIn();

            addToTimebombs();
        }


        private void spawnLargeChest() {
            Location chestSpawnLoc = entity.getLocation().clone().add(0, 1, 0);
            leftSideChest = chestSpawnLoc.getBlock().getRelative(BlockFace.DOWN);
            RightSideChest = leftSideChest.getRelative(BlockFace.NORTH);
            leftSideChest.setType(CompMaterial.CHEST.getMaterial());
            RightSideChest.setType(CompMaterial.CHEST.getMaterial());
            if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_13))
                NewerSpigotAPI.mergeChest(leftSideChest, RightSideChest);
        }

        private void clearUpperBlocks() {
            leftSideChest.getRelative(BlockFace.UP).setType(CompMaterial.AIR.getMaterial());
            RightSideChest.getRelative(BlockFace.UP).setType(CompMaterial.AIR.getMaterial());
        }

        private void addToTimebombs() {
            TimeBombChest chest = new TimeBombChest(UHCPlayer.getFromEntity(entity), leftSideChest.getLocation());
            timeBombs.add(chest);
        }

        private void putItemsIn() {
            Chest chestState = ( Chest ) leftSideChest.getState();
            Inventory inventory = chestState.getInventory();
            for (int i = 0; i < 54; i++) {
                if (i >= drops.size())
                    break;
                inventory.setItem(i, drops.get(i));
            }
        }
    }

    class ChestExplodeTicker extends BukkitRunnable {

        @Override
        public void run() {
            Set<TimeBombChest> timeBombs = Sets.newHashSet(ScenarioTimeBomb.timeBombs);
            timeBombs.forEach(TimeBombChest::tick);
        }
    }

    @RequiredArgsConstructor
    public class TimeBombChest {
        private final UHCPlayer owner;
        private final Location location;
        private int time;

        public void tick() {
            time++;
            int secondUntilExplode = explodeAfterSeconds - time;

            if (secondUntilExplode <= 0)
                explode();
            else if (Extra.isBetween(secondUntilExplode, 3, 5))
                warnSound.play(location);
            else if (Extra.isBetween(secondUntilExplode, 1, 2))
                warnSoundDanger.play(location);
        }

        public void explode() {
            Block block = location.getBlock();
            World world = location.getWorld();

            createExplosion(location);
            world.strikeLightning(location);

            Material air = CompMaterial.AIR.getMaterial();
            block.setType(air);
            block.getRelative(BlockFace.NORTH).setType(air);

            Chat.broadcast(explodedMessage.replace("{player}", owner.getName()));
            timeBombs.remove(this);
        }

        private void createExplosion(Location location) {
            location = WorldUtils.centerOfBlock(location);
            final boolean setFire = false;

            // 只有使用 x,y,z 作為參數的方法才能支援 1.8 ~ 最新版本
            // createExplosion(Location loc, float power, boolean setFire) 只在 1.14+ 才有
            location.getWorld().createExplosion(
                    location.getX(), location.getY(), location.getZ(),
                    explosionPower,
                    setFire,
                    explodedBlocks
            );
        }
    }

}
