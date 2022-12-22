package org.mcwonderland.uhc.game.player.role.player;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.game.player.role.models.RoleApplier;
import org.mcwonderland.uhc.util.GameUtils;
import org.mcwonderland.uhc.util.Lobby;
import org.bukkit.entity.Player;

public class RolePlayerApplier implements RoleApplier {

    @Override
    public void apply(Player player) {
        if (GameUtils.isWaiting()) {
            DaTouNMS.getCommonNMS().setCanPickupExp(player, true);
            Lobby.stuff(player);
        }
    }

}
