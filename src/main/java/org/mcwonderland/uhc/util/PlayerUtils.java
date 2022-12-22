package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import me.lulu.datounms.DaTouNMS;
import me.lulu.datounms.model.ArmorInfo;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.ReflectionUtil;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;
import org.mineacademy.fo.remain.Remain;
import org.mineacademy.fo.remain.nbt.ObjectCreator;

/**
 * 2019-12-08 上午 09:16
 */

@UtilityClass
public class PlayerUtils {

    public Player getShooter(Entity damager) {
        if (!(damager instanceof Projectile))
            return null;

        return getShooter(( Projectile ) damager);
    }

    public Player getShooter(Projectile projectile) {
        if (projectile.getShooter() instanceof Player)
            return ( Player ) projectile.getShooter();

        return null;
    }

    public UHCPlayer getUHCShooter(Projectile projectile) {
        Player shooter = getShooter(projectile);

        if (shooter != null)
            return UHCPlayer.getUHCPlayer(shooter);

        return null;
    }

    public double getFullHealth(LivingEntity entity) {
        double health = entity.getHealth();
        double absorption = 0;

        if (entity instanceof Player)
            absorption = DaTouNMS.getCommonNMS().getAbsorptionHeart(( Player ) entity);

        return health + absorption;
    }

    public void costPlayerToolDurability(Player p) {
        ItemStack itemInHand = p.getItemInHand();
        if (Extra.isDamageable(itemInHand.getType())) {
            itemInHand.setDurability(( short ) (itemInHand.getDurability() + 1));
            if (isItemReachedMaxDurability(itemInHand)) {
                itemInHand.setAmount(0);
                p.updateInventory();
                CompSound.ITEM_BREAK.play(p, 0.5F, 1F);
            }
        }
    }

    private boolean isItemReachedMaxDurability(ItemStack i) {
        return i.getDurability() > i.getType().getMaxDurability();
    }

    public void breakBlockNms(Player player, Block toBreak) {
        Object entityPlayer = Remain.getHandleEntity(player);
        Object playerInteractManager = ReflectionUtil.getFieldContent(entityPlayer, "playerInteractManager");
        Object blockPosition = ObjectCreator.NMS_BLOCKPOSITION.getInstance(toBreak.getX(), toBreak.getY(), toBreak.getZ());

        Extra.playBlockBreakEffect(toBreak.getLocation(), toBreak.getType());
        ReflectionUtil.invoke("breakBlock", playerInteractManager, blockPosition);
    }

    public double getArmorPoints(LivingEntity livingEntity) {
        ItemStack[] armors = livingEntity.getEquipment().getArmorContents();
        double totalPoints = 0;

        for (ItemStack itemStack : armors) {
            if (itemStack != null)
                totalPoints += getArmorPoints(itemStack);
        }

        return totalPoints;
    }

    private double getArmorPoints(ItemStack itemStack) {
        try {
            CompMaterial material = CompMaterial.fromMaterial(itemStack.getType());
            ArmorInfo info = ArmorInfo.fromMaterial(material.getMaterial());
            return DaTouNMS.getCommonNMS().getArmorPoint(info);
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    public boolean isShieldBlocked(EntityDamageEvent e) {
        return MinecraftVersion.atLeast(MinecraftVersion.V.v1_9) && e.getOriginalDamage(EntityDamageEvent.DamageModifier.BLOCKING) < 0;
    }
}
