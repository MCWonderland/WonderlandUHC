package org.mcwonderland.uhc.scenario.impl.special;

import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageEvent;
import org.mcwonderland.uhc.api.event.player.UHCPlayerRespawnedEvent;
import org.mcwonderland.uhc.api.event.timer.FinalHealEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioIronMan extends ConfigBasedScenario implements Listener {
    private static final Set<UHCPlayer> damaged = new HashSet<>();
    private static final Set<UHCPlayer> ironMen = new HashSet<>();

    @FilePath(name = "Extra_Heal")
    private Integer extraHeal;
    @FilePath(name = "Damage_Before_Final_Heal")
    private String damageBeforeFinalHeal;

    public ScenarioIronMan(ScenarioName name) {
        super(name);
    }


    @Override
    public void onDisable() {
        ironMen.forEach(uhcPlayer -> {
            Player player = uhcPlayer.getPlayer();

            if (player != null) {
                Extra.setMaxHealth(player, Extra.getMaxHealth(player) - extraHeal);
            }
        });
    }

    @Override
    public void onEnable() {
        ironMen.forEach(uhcPlayer -> {
            Player player = uhcPlayer.getPlayer();
            changeMaxHeath(player);
        });
    }

    @EventHandler
    public void onFinalHeal(FinalHealEvent event) {
        ironMen.addAll(UHCPlayers.getBy(uhcPlayer -> !uhcPlayer.isDead() && !damaged.contains(uhcPlayer)));
        ironMen.forEach(uhcPlayer -> changeMaxHeath(uhcPlayer.getEntity()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void checkDamage(UHCPlayerDamageEvent e) {
        if (!e.isCancelled()
                && !PlayerUtils.isShieldBlocked(e.getEvent())
                && !Game.getGame().isFinalHealEnabled()) {
            UHCPlayer uhcPlayer = e.getUhcPlayer();


            if (damaged.add(uhcPlayer))
                Chat.send(uhcPlayer.getPlayer(), damageBeforeFinalHeal);
        }
    }

    @EventHandler
    public void onRespawn(UHCPlayerRespawnedEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();

        if (ironMen.contains(uhcPlayer)) {
            changeMaxHeath(uhcPlayer.getEntity());
        }
    }

    private void changeMaxHeath(LivingEntity entity) {
        if (entity != null)
            Extra.setMaxHealth(entity, Extra.getMaxHealth(entity) + extraHeal);
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list).replace("{heal}", extraHeal);
    }
}
