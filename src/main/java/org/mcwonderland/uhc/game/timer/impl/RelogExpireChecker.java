package org.mcwonderland.uhc.game.timer.impl;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.game.border.BorderType;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.timer.SecondTimer;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.BorderUtil;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.mineacademy.fo.Common;

public class RelogExpireChecker extends SecondTimer {

    @Override
    public void run() {
        for (CombatRelog relog : CombatRelog.getRelogs()) {
            UHCPlayer uhcPlayer = relog.getUhcPlayer();
            LivingEntity relogEntity = relog.getEntity();

            if (relog.getTime() <= 0) {
                Chat.broadcast(Messages.Game.RELOG_DEATH
                        .replace("{player}", uhcPlayer.getName())
                        .replace("{playerKills}", uhcPlayer.getStats().kills + "")
                        .replace("{minute}", Settings.CombatRelog.RELOG_IN_MINUTES + ""));
                killAndRemove(relog);
            } else if (GameUtils.getBorderType() == BorderType.MOVING &&
                    !BorderUtil.isInBorder(relogEntity.getLocation()))
                relogEntity.damage(2.0);

            relog.setTime(relog.getTime() - 1);
        }
    }

    private static void killAndRemove(CombatRelog relog) {
        Common.callEvent(new EntityDeathEvent(relog.getEntity(), Lists.newArrayList()));
        relog.getEntity().remove();
        relog.remove();
    }

}
