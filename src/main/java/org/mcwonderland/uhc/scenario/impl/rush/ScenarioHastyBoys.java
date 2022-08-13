package org.mcwonderland.uhc.scenario.impl.rush;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.CompMaterial;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioHastyBoys extends ConfigBasedScenario implements Listener {

    public ScenarioHastyBoys(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPrepareCraft(PrepareItemCraftEvent e) {
        ItemStack result = e.getInventory().getResult();

        if (result == null)
            return;

        CompMaterial craftItemType = CompMaterial.fromMaterial(result.getType());

        if (isTool(craftItemType))
            e.getInventory().setResult(applyHastyEnchants(result));
    }

    private ItemStack applyHastyEnchants(ItemStack result) {
        result.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
        return result;
    }

    private boolean isTool(CompMaterial type) {
        String typeName = type.toString();

        return typeName.contains("AXE")
                || typeName.contains("SHOVEL")
                || typeName.contains("PICKAXE")
                || typeName.contains("HOE");
    }

}
