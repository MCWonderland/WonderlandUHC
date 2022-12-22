package org.mcwonderland.uhc.scenario.impl.block;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.WorldUtils;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioTripleOres extends ConfigBasedScenario implements Listener {

    public ScenarioTripleOres(ScenarioName name) {
        super(name);
    }

    @EventHandler
    protected void onBlockBreak(UHCBlockBreakEvent e) {
        Block block = e.getBlock();

        if (WorldUtils.isOre(block.getType()))
            e.getDrops().forEach(itemStack -> itemStack.setAmount(itemStack.getAmount() * 3));
    }
}
