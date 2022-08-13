package org.mcwonderland.uhc.scenario.impl.damage;

import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageByEntityEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.fo.model.SimpleSound;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioSwitcheroo extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Switch_Sound")
    private SimpleSound switchSound;

    public ScenarioSwitcheroo(ScenarioName name) {
        super(name);
    }


    @EventHandler
    public void onGamingEntityDamage(UHCPlayerDamageByEntityEvent e) {
        Player shooter = PlayerUtils.getShooter(e.getDamager());

        if (shooter == null)
            return;

        switchPlayerLocation(e.getUhcPlayer().getEntity(), shooter);
    }

    private void switchPlayerLocation(LivingEntity p1, LivingEntity p2) {
        Location p1Location = p1.getLocation();
        Location p2Location = p2.getLocation();

        p1.teleport(p2Location);
        p2.teleport(p1Location);

        sound(switchSound, p1, p2);
    }

    private void sound(SimpleSound sound, LivingEntity... entities) {
        for (LivingEntity entity : entities) {
            if (entity instanceof Player)
                Extra.sound(( Player ) entity, sound);
        }
    }
}
