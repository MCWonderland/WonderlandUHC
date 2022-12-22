package org.mcwonderland.uhc.scenario.impl.block;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.model.VeinMiner;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.WorldUtils;
import org.mcwonderland.uhc.util.cuboid.SelectMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioVeinMiners extends ConfigBasedScenario implements Listener {

    public ScenarioVeinMiners(ScenarioName name) {
        super(name);
    }

    @EventHandler
    protected void onBlockBreak(UHCBlockBreakEvent e) {
        Block block = e.getBlock();
        Material blockType = block.getType();
        Player player = e.getPlayer();

        if ((WorldUtils.isOre(blockType)) && player.isSneaking())
            VeinMiner.mineVeins(block, player, SelectMode.CONNECT);
    }
}
