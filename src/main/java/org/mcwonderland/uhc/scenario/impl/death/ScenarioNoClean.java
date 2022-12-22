package org.mcwonderland.uhc.scenario.impl.death;

import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.model.InvinciblePlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioNoClean extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Invincible_Seconds")
    private Integer invincibleSeconds;

    public ScenarioNoClean(ScenarioName name) {
        super(name);
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replaceTime(invincibleSeconds);
    }

    @EventHandler
    public void onGamingEntityDeath(UHCGamingDeathEvent e) {
        Player killer = e.getEntity().getKiller();

        if (killer == null)
            return;

        InvinciblePlayer.addInvincible(UHCPlayer.getUHCPlayer(killer), invincibleSeconds);
    }
}
