package org.mcwonderland.uhc.game.player.role.player;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.player.role.models.RoleHider;
import org.bukkit.entity.Player;

public class RolePlayerHider extends RoleHider {

    @Override
    protected void checkHide() {
        for (UHCPlayer other : UHCPlayers.getOnlines()) {
            Player otherPlayer = other.getPlayer();

            if (other.isDead())
                hide(otherPlayer);
            else
                show(otherPlayer);

            visFor(otherPlayer);
        }
    }

}
