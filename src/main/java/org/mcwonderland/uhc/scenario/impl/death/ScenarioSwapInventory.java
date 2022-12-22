package org.mcwonderland.uhc.scenario.impl.death;

import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.model.InventoryContent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioSwapInventory extends ConfigBasedScenario implements Listener {

    public ScenarioSwapInventory(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void handleSwapInventory(UHCGamingDeathEvent e) {

        Player player = e.getUhcPlayer().getPlayer();
        Player killer = e.getEntity().getKiller();

        if (killer == null)
            return;

        CombatRelog relog = CombatRelog.getByRelogEntity(e.getEntity());

        if (relog != null) {
            relog.setInventoryContent(new InventoryContent(killer.getInventory()));
        }

        swapInventory(player, killer, e.getDrops());
    }

    private void swapInventory(Player deathPlayer, Player killer, List<ItemStack> drops) {
        PlayerInventory deathPlayerInv = deathPlayer.getInventory();
        PlayerInventory killerInv = killer.getInventory();

        InventoryContent deathPlayerInvContent = new InventoryContent(deathPlayerInv);
        InventoryContent killerInvContent = new InventoryContent(killerInv);

        drops.removeAll(Arrays.asList(deathPlayerInvContent.getAllItems()));
        drops.addAll(Arrays.asList(killerInvContent.getAllItems()));

        killerInvContent.setContents(deathPlayerInv);
        deathPlayerInvContent.setContents(killerInv);
    }
}
