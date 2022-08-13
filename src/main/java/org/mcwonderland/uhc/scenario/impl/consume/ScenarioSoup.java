package org.mcwonderland.uhc.scenario.impl.consume;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.Remain;

import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioSoup extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Regen_Health")
    private Integer soupRegenHealth;

    public ScenarioSoup(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = e.getItem();

        if (needRegen(player) && isSoup(itemInHand))
            soupRegen(player, itemInHand);
    }

    private boolean needRegen(Player player) {
        return player.getHealth() < Remain.getMaxHealth(player);
    }

    private boolean isSoup(ItemStack itemInHand) {
        return itemInHand != null && itemInHand.getType() == CompMaterial.MUSHROOM_STEW.getMaterial();
    }

    private void soupRegen(Player player, ItemStack soupItem) {
        soupItem.setType(CompMaterial.BOWL.getMaterial());
        player.setHealth(MathUtil.range(player.getHealth() + soupRegenHealth, 0, Remain.getMaxHealth(player)));
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replace("{heal}", soupRegenHealth);
    }
}
