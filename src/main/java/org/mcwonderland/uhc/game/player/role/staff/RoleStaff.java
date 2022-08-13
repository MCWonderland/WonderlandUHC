package org.mcwonderland.uhc.game.player.role.staff;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.role.Role;

public class RoleStaff extends Role {

    public RoleStaff() {
        super(RoleName.STAFF,
                new RoleStaffEvents(),
                new RoleStaffApplier(),
                new StaffChat(),
                new RoleStaffHider(),
                new RoleStaffBoard()
        );
    }
}
