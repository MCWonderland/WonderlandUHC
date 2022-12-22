package org.mcwonderland.uhc.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.api.event.player.UHCPlayerEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.util.WorldUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2019-12-10 上午 09:02
 */
@Getter
public class UHCBlockBreakEvent extends UHCPlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    @Getter(AccessLevel.PRIVATE)
    private final BlockBreakEvent blockBreakEvent;
    private final List<ItemStack> drops;
    @Getter(AccessLevel.PRIVATE)
    private final List<ItemStack> originalDrops;
    @Setter
    private int expToDrop;
    @Setter
    private boolean handleCustom;

    public UHCBlockBreakEvent(BlockBreakEvent blockBreakEvent) {
        super(UHCPlayer.getUHCPlayer(blockBreakEvent.getPlayer()));
        this.blockBreakEvent = blockBreakEvent;
        this.drops = new ArrayList<>(blockBreakEvent.getBlock().getDrops(blockBreakEvent.getPlayer().getItemInHand()));
        this.originalDrops = cloneItemStacks();
        this.expToDrop = blockBreakEvent.getExpToDrop();
    }

    private List<ItemStack> cloneItemStacks() {
        return drops.stream().map(ItemStack::clone).collect(Collectors.toList());
    }

    public Player getPlayer() {
        return getUhcPlayer().getPlayer();
    }

    public Block getBlock() {
        return blockBreakEvent.getBlock();
    }

    public Material getBlockType() {
        return blockBreakEvent.getBlock().getType();
    }

    public void removeDrop(CompMaterial... materials) {
        for (CompMaterial material : materials)
            removeDrop(material.getMaterial());
    }

    public void removeDrop(Material... materials) {
        for (Material material : materials)
            replaceDrop(material, CompMaterial.AIR.getMaterial());
    }

    public void replaceDrop(CompMaterial from, CompMaterial to) {
        replaceDrop(from.getMaterial(), to.getMaterial());
    }

    public void replaceDrop(Material from, Material to) {
        WorldUtils.replaceDrop(drops, from, to);
    }

    public boolean isDropsModified() {
        if (handleCustom)
            return true;

        if (originalDrops.size() != drops.size())
            return true;

        if (expToDrop != blockBreakEvent.getExpToDrop())
            return true;

        for (int i = 0; i < originalDrops.size(); i++) {
            ItemStack ori = originalDrops.get(i);
            ItemStack compare = drops.get(i);

            if (ori.getAmount() != compare.getAmount() || !ori.isSimilar(compare))
                return true;
        }

        return false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}