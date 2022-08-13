package org.mcwonderland.uhc.game.player.role;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.role.player.RolePlayer;
import org.mcwonderland.uhc.game.player.role.spectator.RoleSpectator;
import org.mcwonderland.uhc.game.player.role.staff.RoleStaff;

public class SimpleRoleFactory {

    public static Role getRole(RoleName rolename) {
        switch (rolename) {
            case STAFF:
                return new RoleStaff();
            case PLAYER:
                return new RolePlayer();
            case SPECTATOR:
                return new RoleSpectator();
            default:
                return null;
        }
    }

}
