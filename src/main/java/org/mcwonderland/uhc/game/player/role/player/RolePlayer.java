package org.mcwonderland.uhc.game.player.role.player;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.role.Role;

public class RolePlayer extends Role {

    public RolePlayer() {
        super(
                RoleName.PLAYER,
                new RolePlayerEvents(),
                new RolePlayerApplier(),
                new PlayerChat(),
                new RolePlayerHider(),
                new RolePlayerBoard()
        );
    }
}
