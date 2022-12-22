package org.mcwonderland.uhc.scenario.impl.special;

import lombok.Getter;
import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 2019-12-07 下午 01:18
 */
@Getter
public class ScenarioBackPack extends ConfigBasedScenario implements Listener {
    @FilePath(name = "Cant_Use_Msg")
    private String cantUseMsg;
    @FilePath(name = "Size")
    private Integer size;

    private Map<UHCTeam, Inventory> backpacks = new HashMap<>();

    public ScenarioBackPack(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onGamingEntityDeath(UHCGamingDeathEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();

        UHCTeam team = uhcPlayer.getTeam();

        if (team.isEliminate())
            dropBackpackItems(team.getBackpack(), e.getDrops());
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replace("{cmd}", "backpack");
    }

    private void dropBackpackItems(Inventory backpack, List<ItemStack> drops) {
        drops.addAll(Arrays.asList(backpack.getContents()));
    }
}
