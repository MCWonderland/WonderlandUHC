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
public class ScenarioDiamondLess extends ConfigBasedScenario implements Listener {

    public ScenarioDiamondLess(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onBlockBreak(UHCBlockBreakEvent e) {
        if (e.getBlockType() == CompMaterial.DIAMOND_ORE.getMaterial())
            e.removeDrop(CompMaterial.DIAMOND, CompMaterial.DIAMOND_ORE);
    }
}
