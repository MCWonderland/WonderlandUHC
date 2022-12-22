package org.mcwonderland.uhc.scenario.impl.rush;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.WorldUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioCutClean extends ConfigBasedScenario implements Listener {

    public ScenarioCutClean(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handleCutClean(UHCBlockBreakEvent e) {
        replaceOreDrops(e);

        if (e.isDropsModified())
            e.setExpToDrop(WorldUtils.getBlockEXP(e.getBlockType()));
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        EntityType entityType = e.getEntityType();
        List<ItemStack> drops = e.getDrops();

        replaceEntityDrops(entityType, drops);
    }

    private void replaceOreDrops(UHCBlockBreakEvent e) {
        e.replaceDrop(CompMaterial.IRON_ORE, CompMaterial.IRON_INGOT);
        e.replaceDrop(CompMaterial.GOLD_ORE, CompMaterial.GOLD_INGOT);
        e.replaceDrop(CompMaterial.GRAVEL, CompMaterial.FLINT);
        e.replaceDrop(CompMaterial.FLINT, CompMaterial.FLINT);
    }

    private void replaceEntityDrops(EntityType entityType, List<ItemStack> drops) {
        switch (entityType) {
            case CHICKEN:
                WorldUtils.replaceDrop(drops, CompMaterial.CHICKEN, CompMaterial.COOKED_CHICKEN);
                break;
            case COW:
                WorldUtils.replaceDrop(drops, CompMaterial.BEEF, CompMaterial.COOKED_BEEF);
                break;
            case PIG:
                WorldUtils.replaceDrop(drops, CompMaterial.PORKCHOP, CompMaterial.COOKED_PORKCHOP);
                break;
            case SHEEP:
                WorldUtils.replaceDrop(drops, CompMaterial.MUTTON, CompMaterial.COOKED_MUTTON);
                break;
            case RABBIT:
                WorldUtils.replaceDrop(drops, CompMaterial.RABBIT, CompMaterial.COOKED_RABBIT);
                break;
        }
    }
}
