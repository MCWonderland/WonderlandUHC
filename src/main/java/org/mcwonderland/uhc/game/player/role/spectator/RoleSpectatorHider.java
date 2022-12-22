package org.mcwonderland.uhc.game.player.role.spectator;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.player.role.models.RoleHider;
import org.bukkit.entity.Player;

public class RoleSpectatorHider extends RoleHider {

    @Override
    protected void checkHide() {
        for (UHCPlayer other : UHCPlayers.getOnlines()) {
            Player otherPlayer = other.getPlayer();

            if (other.getRoleName() == RoleName.SPECTATOR) { //為了以後的spec option做準備
                hide(otherPlayer);
                invisFor(otherPlayer);
            } else if (other.getRoleName() == RoleName.STAFF) {
                hide(otherPlayer);

                if (other.getStaffOptions().isShowSpectator())
                    visFor(otherPlayer);
                else
                    invisFor(otherPlayer);

            } else  // 玩家
                invisFor(otherPlayer);
        }
    }
}
