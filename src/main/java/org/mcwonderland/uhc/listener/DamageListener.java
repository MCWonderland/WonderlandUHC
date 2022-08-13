package org.mcwonderland.uhc.listener;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageByEntityEvent;
import org.mcwonderland.uhc.api.event.player.UHCPlayerDamageEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.mineacademy.fo.Common;

/**
 * 2019-12-08 下午 07:33
 */
public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void arrowViewHealth(UHCPlayerDamageByEntityEvent e) {
        if (!Settings.Misc.ARROW_HEALTH)
            return;

        UHCPlayer uhcPlayer = e.getUhcPlayer();
        Player shooter = PlayerUtils.getShooter(e.getDamager());

        if (!(e.getDamager() instanceof Projectile)
                || shooter == null
                || uhcPlayer.getPlayer() != shooter)
            return;

        double hp = Extra.formatHealth(PlayerUtils.getFullHealth(uhcPlayer.getPlayer()));

        Chat.send(shooter, Messages.Game.ARROW_HEALTH_MESSAGE
                .replace("{player}", uhcPlayer.getName())
                .replace("{heal}", "" + hp));
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof LivingEntity))
            return;

        UHCPlayer uhcPlayer = UHCPlayer.getFromEntity(e.getEntity());

        if (uhcPlayer != null && uhcPlayer.getRoleName() == RoleName.PLAYER)
            handleCustomDamageEvents(uhcPlayer, e);
        else
            cancelIfIsPlayer(e);
    }

    private void cancelIfIsPlayer(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player)
            e.setCancelled(true);
    }

    private void handleCustomDamageEvents(UHCPlayer uhcPlayer, EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent)
            Common.callEvent(new UHCPlayerDamageByEntityEvent(uhcPlayer, ( EntityDamageByEntityEvent ) e));
        else
            Common.callEvent(new UHCPlayerDamageEvent(uhcPlayer, e));
    }
}
