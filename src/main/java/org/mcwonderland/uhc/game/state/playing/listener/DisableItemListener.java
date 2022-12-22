package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCItemSettings;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.ItemSimilarChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.model.BrewResultGetter;

public class DisableItemListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCraft(CraftItemEvent e) {
        DisabledChecker.check(e, (Player) e.getWhoClicked(), e.getCurrentItem());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();

        if (item == null || Tool.getTool(item) != null)
            return;

        DisabledChecker.check(e, e.getPlayer(), item);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBrew(BrewEvent e) {
        BrewerInventory contents = e.getContents();
        ItemStack ingredient = contents.getIngredient();
        ItemStack[] items = contents.getContents();

        for (ItemStack item : items) {
            if (item != null) {
                DisabledChecker.check(e, null, BrewResultGetter.getBrewResult(item, ingredient));

                if (e.isCancelled())
                    return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent e) {
        DisabledChecker.check(e, e.getPlayer(), e.getItem());
    }

    private static class DisabledChecker {
        private static Cancellable event;
        private static Player player;
        private static ItemStack[] toChecks;

        public static void check(Cancellable event, @Nullable Player player, ItemStack... itemStacks) {
            DisabledChecker.event = event;
            DisabledChecker.player = player;
            DisabledChecker.toChecks = itemStacks;

            cancelIfItemDisabled();
        }

        private static void cancelIfItemDisabled() {
            if (isDisabledItems()) {
                event.setCancelled(true);
                tellDisabled();
            }
        }

        private static void tellDisabled() {
            if (player != null) {
                Chat.send(player, Messages.Game.ITEM_DISABLED);
                Extra.sound(player, Sounds.Game.ITEM_DISABLED);
            }
        }

        private static boolean isDisabledItems() {
            UHCItemSettings itemSettings = Game.getSettings().getItemSettings();

            return itemSettings.getCustomDisabledItems()
                    .stream()
                    .anyMatch(disableItem -> {
                        for (ItemStack item : toChecks) {
                            if (ItemSimilarChecker.isSimilar(item, disableItem))
                                return true;
                        }
                        return false;
                    });
        }
    }
}
