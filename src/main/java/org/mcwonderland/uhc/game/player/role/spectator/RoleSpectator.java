package org.mcwonderland.uhc.game.player.role.spectator;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.role.Role;

public class RoleSpectator extends Role {

    public RoleSpectator() {
        super(RoleName.SPECTATOR,
                new RoleSpectatorEvents(),
                new RoleSpectatorApplier(),
                new SpectatorChat(),
                new RoleSpectatorHider(),
                new RoleSpectatorBoard()
        );
    }
}
