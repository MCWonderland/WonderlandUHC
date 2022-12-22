package org.mcwonderland.uhc.scenario.impl.block;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.fo.remain.CompMaterial;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioGoldLess extends ConfigBasedScenario implements Listener {

    public ScenarioGoldLess(ScenarioName name) {
        super(name);
    }

    @EventHandler
    protected void onBlockBreak(UHCBlockBreakEvent e) {
        if (e.getBlockType() == CompMaterial.GOLD_ORE.getMaterial())
            e.removeDrop(CompMaterial.GOLD_ORE);
    }
}
