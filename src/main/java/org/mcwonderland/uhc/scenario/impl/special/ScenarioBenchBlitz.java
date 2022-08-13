package org.mcwonderland.uhc.scenario.impl.special;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioBenchBlitz extends ConfigBasedScenario implements Listener {

    private static final Set<UUID> crafted = new HashSet<>();
    @FilePath(name = "Workbench_Created")
    private String workbenchCreated;
    @FilePath(name = "Workbench_Created_Sound")
    private SimpleSound workbenchCreatedSound;

    public ScenarioBenchBlitz(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCraftItem(CraftItemEvent e) {
        Player player = ( Player ) e.getWhoClicked();

        if (e.getCurrentItem().getType() != CompMaterial.CRAFTING_TABLE.getMaterial())
            return;

        if (isBenchCrafted(player))
            e.setCancelled(true);
        else
            craftBench(player);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        ItemStack result = e.getInventory().getResult();

        if (result == null || result.getType() != CompMaterial.CRAFTING_TABLE.getMaterial())
            return;

        for (HumanEntity viewer : e.getViewers()) {
            if (isBenchCrafted(viewer))
                e.getInventory().setResult(CompMaterial.AIR.toItem());
        }
    }

    private boolean isBenchCrafted(HumanEntity player) {
        return crafted.contains(player.getUniqueId());
    }

    private void craftBench(Player player) {
        putCraftedData(player);
        Chat.send(player, workbenchCreated);
        Extra.sound(player, workbenchCreatedSound);
    }

    private void putCraftedData(Player player) {
        crafted.add(player.getUniqueId());
    }
}
