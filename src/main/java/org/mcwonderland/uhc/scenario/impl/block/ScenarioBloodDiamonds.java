package org.mcwonderland.uhc.scenario.impl.block;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioBloodDiamonds extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Damage")
    private Integer damage;

    public ScenarioBloodDiamonds(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onBlockBreak(UHCBlockBreakEvent e) {
        if (e.getBlockType() == CompMaterial.DIAMOND_ORE.getMaterial())
            e.getPlayer().damage(1.0);
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replace("{heal}", damage);
    }
}
