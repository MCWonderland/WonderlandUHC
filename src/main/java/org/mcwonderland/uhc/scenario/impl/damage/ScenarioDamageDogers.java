package org.mcwonderland.uhc.scenario.impl.damage;

import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;

import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioDamageDogers extends ConfigBasedScenario implements Listener {
    private static int numberOfDead;

    @FilePath(name = "Amount")
    private Integer amount;
    @FilePath(name = "Death_Cause_This")
    private String deathCauseThis;
    @FilePath(name = "Death_Cause_This_Sound")
    private SimpleSound deathCauseThisSound;

    public ScenarioDamageDogers(ScenarioName name) {
        super(name);
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replace("{amount}", amount);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(UHCPlayerDamageEvent e) {
        if (dontNeedMoreToDie())
            return;

        if (PlayerUtils.isShieldBlocked(e.getEvent()))
            return;

        killGamingEntity(e.getUhcPlayer().getEntity());
    }

    private boolean dontNeedMoreToDie() {
        return numberOfDead >= amount;
    }

    private void killGamingEntity(LivingEntity entity) {
        numberOfDead++;
        entity.setHealth(0);
        Chat.broadcast(deathCauseThis
                .replace("{player}", entity.getName())
                .replace("{amount}", (amount - numberOfDead) + "")
        );
        Extra.sound(deathCauseThisSound);
    }
}
