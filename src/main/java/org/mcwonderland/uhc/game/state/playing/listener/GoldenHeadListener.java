package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.PotionApplier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GoldenHeadListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEatGoldenHead(PlayerItemConsumeEvent e) {
        ItemStack item = e.getItem();
        Player player = e.getPlayer();
        String name = getItemName(item);

        if (name != null && name.equalsIgnoreCase(Settings.Misc.GOLDEN_HEAD_NAME)) {
            PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1);
            PotionEffect absorp = new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20 * 2, 0);

            PotionApplier.addPotionEffect(player, regen);
            PotionApplier.addPotionEffect(player, absorp);
        }
    }

    private String getItemName(ItemStack item) {
        try {
            return item.getItemMeta().getDisplayName();
        } catch (Exception e) {
            return "";
        }
    }
}
