package org.mcwonderland.uhc.scenario.impl.special;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompMetadata;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioTripleArrow extends ConfigBasedScenario implements Listener {

    private static String NO_PICKUP_TAG = "wonderlanduhc_arrow_no_pickup";

    public ScenarioTripleArrow(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onArrowShoot(EntityShootBowEvent e) {
        LivingEntity shooter = e.getEntity();

        if (shooter instanceof Player)
            tripleArrows(e.getProjectile(), ( Player ) shooter);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile entity = e.getEntity();

        if (CompMetadata.hasTempMetadata(entity, NO_PICKUP_TAG)) {
            entity.remove();
        }
    }

    private void tripleArrows(Entity projectile, Player shooter) {
        Vector v = projectile.getVelocity();
        double vectorX = v.getX();

        v.setX(vectorX - 0.5);
        shootArrow(shooter, v);
        v.setX(vectorX + 0.5);
        shootArrow(shooter, v);
    }

    private void shootArrow(Player shooter, Vector v) {
        ItemStack arrowItem = getArrowsInInventory(shooter);

        if (arrowItem == null)
            return;

        Arrow arrow = shooter.launchProjectile(Arrow.class, v);
        CompMetadata.setTempMetadata(arrow, NO_PICKUP_TAG);

        if (!isBowInfinity(shooter))
            arrowItem.setAmount(arrowItem.getAmount() - 1);
    }

    private boolean isBowInfinity(Player shooter) {
        return shooter.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE) > 0;
    }

    private ItemStack getArrowsInInventory(Player shooter) {
        return PlayerUtil.getFirstItem(shooter, CompMaterial.ARROW.toItem());
    }
}
