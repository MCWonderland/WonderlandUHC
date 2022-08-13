package org.mcwonderland.uhc.scenario.impl.rush;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockVector;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioFastSmelting extends ConfigBasedScenario implements Listener {

    private final List<BlockVector> boosted = new ArrayList<>();
    private final short speedUp = 2;

    public ScenarioFastSmelting(ScenarioName name) {
        super(name);
    }


    @EventHandler
    public void onFurnace(FurnaceBurnEvent event) {
        Block block = event.getBlock();
        BlockVector blockVector = block.getLocation().toVector().toBlockVector();

        if (!(boosted.contains(blockVector))) {
            boosted.add(blockVector);
            increaseFurnaceSpeed(block, blockVector);
        }
    }

    private void increaseFurnaceSpeed(Block block, BlockVector blockVector) {
        Common.runTimer(0, 1, new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != CompMaterial.FURNACE.getMaterial()) {
                    this.cancel();
                    return;
                }

                Furnace furnace = ( Furnace ) block.getState();
                furnace.setBurnTime(( short ) (furnace.getBurnTime() - speedUp + speedUp / 10));

                if (needUpdate(furnace)) {
                    furnace.setCookTime(( short ) (furnace.getCookTime() + speedUp));
                } else {
                    boosted.remove(blockVector);
                    cancel();
                }
                furnace.update();
            }
        });
    }

    private boolean needUpdate(Furnace furnace) {
        ItemStack smelting = furnace.getInventory().getSmelting();
        boolean hasItemToSmelt = smelting != null && smelting.getType() != CompMaterial.AIR.getMaterial();
        boolean isFueling = furnace.getInventory().getFuel() != null || furnace.getBurnTime() > 0;


        return hasItemToSmelt
                && isFueling
                && (furnace.getCookTime() > 0 || furnace.getBurnTime() > 0);
    }
}
