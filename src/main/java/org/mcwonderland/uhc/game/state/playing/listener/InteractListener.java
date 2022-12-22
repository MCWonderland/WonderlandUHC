package org.mcwonderland.uhc.game.state.playing.listener;

import lombok.val;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.util.cuboid.Cuboid;
import org.mcwonderland.uhc.util.cuboid.SelectMode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.mineacademy.fo.remain.CompMaterial;

public class InteractListener implements Listener {

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent e) {
        val uhcPlayer = UHCPlayer.getUHCPlayer(e.getPlayer());

        if (uhcPlayer.getRole().getName() == RoleName.SPECTATOR)
            e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
        final Block clickedBlock = e.getClickedBlock();
        final Material type = clickedBlock == null ? Material.AIR : clickedBlock.getType();
        final Action action = e.getAction();

        if (action == Action.PHYSICAL) {
            if (uhcPlayer.isDead()) {
                if (type == CompMaterial.FARMLAND.getMaterial()
                        || type == CompMaterial.TRIPWIRE.getMaterial()
                        || type.toString().contains("PLATE")) {
                    e.setCancelled(true);
                }
            }
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            if (uhcPlayer.isDead()) {
                if (type.toString().contains("CHEST")) {
                    e.setCancelled(true);

                    if (uhcPlayer.getRoleName() == RoleName.STAFF) {
                        Chest chest = ( Chest ) clickedBlock.getState();
                        Inventory inv = Bukkit.createInventory(null, chest.getInventory().getSize(), "Chest");
                        inv.setContents(chest.getInventory().getContents());
                        player.openInventory(inv);
                    }
                } else if (type.toString().contains("DOOR"))
                    e.setCancelled(true);
            }
        } else if (action == Action.LEFT_CLICK_BLOCK) {
            if (uhcPlayer.isDead()) {
                Material fire = CompMaterial.FIRE.getMaterial();
                e.setCancelled(Cuboid.getBlocksNearBy(clickedBlock, SelectMode.CUBE)
                        .anyMatch(block -> block.getType() == fire));
            }
        }
    }
}
