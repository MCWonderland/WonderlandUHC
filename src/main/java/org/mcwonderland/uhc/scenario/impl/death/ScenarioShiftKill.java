package org.mcwonderland.uhc.scenario.impl.death;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioShiftKill extends ConfigBasedScenario implements Listener {

    public ScenarioShiftKill(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onGamingEntityDeath(UHCGamingDeathEvent e) {
        LivingEntity entity = e.getEntity();
        Player killer = entity.getKiller();

        if (killer == null)
            return;

        if (!killer.isSneaking())
            costHalfHealth(killer);
    }

    private void costHalfHealth(Player killer) {
        double fullHeath = killer.getHealth() + DaTouNMS.getCommonNMS().getAbsorptionHeart(killer);
        killer.damage(fullHeath / 2);
    }
}
