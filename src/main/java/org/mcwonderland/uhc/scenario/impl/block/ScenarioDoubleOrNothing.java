package org.mcwonderland.uhc.scenario.impl.block;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;

import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioDoubleOrNothing extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Nothing_Sound")
    private SimpleSound nothingSound;
    @FilePath(name = "Trigger_Blocks")
    private List<Material> triggerBlocks;

    public ScenarioDoubleOrNothing(ScenarioName name) {
        super(name);
    }

    @EventHandler
    protected void onBlockBreak(UHCBlockBreakEvent e) {
        Material blockType = e.getBlockType();

        if (triggerBlocks.contains(blockType))
            doubleOrNothing(e);
    }

    private void doubleOrNothing(UHCBlockBreakEvent e) {
        if (RandomUtil.nextBoolean())
            e.getDrops().forEach(drop -> drop.setAmount(drop.getAmount() * 2));
        else {
            e.getDrops().clear();
            Extra.sound(e.getBlock().getLocation(), nothingSound);
        }
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replace("{materials}", triggerBlocks, " - ");
    }
}
