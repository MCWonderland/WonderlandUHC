package org.mcwonderland.uhc.game.player.role.staff;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.player.role.models.RoleHider;
import org.mcwonderland.uhc.game.player.staff.StaffOptions;
import org.bukkit.entity.Player;

public class RoleStaffHider extends RoleHider {
    @Override
    protected void checkHide() {
        StaffOptions option = UHCPlayer.getUHCPlayer(player).getStaffOptions();

        for (UHCPlayer other : UHCPlayers.getOnlines()) {
            Player otherPlayer = other.getPlayer();

            if (other.getRoleName() == RoleName.SPECTATOR) {
                invisFor(otherPlayer);

                if (option.isShowSpectator())  // 如果這位staff是顯示觀察者的
                    show(otherPlayer);
                else
                    hide(otherPlayer);

            } else if (other.getRoleName() == RoleName.STAFF) { //除了顯示staff的staff外,通通隱藏這位玩家
                StaffOptions otherStaffOption = other.getStaffOptions();

                if (otherStaffOption.isShowStaff())  //對方想顯示我
                    visFor(otherPlayer);
                else
                    invisFor(otherPlayer);

                if (option.isShowStaff())  // 我想顯示對方
                    show(otherPlayer);
                else
                    hide(otherPlayer);

            } else  // 玩家
                invisFor(otherPlayer);
        }
    }
}
