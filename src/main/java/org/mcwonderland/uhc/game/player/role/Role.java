package org.mcwonderland.uhc.game.player.role;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.role.models.*;
import org.mcwonderland.uhc.game.player.role.models.*;
import org.bukkit.entity.Player;

public abstract class Role {

    @Getter
    private RoleName name;
    @Getter
    private RoleEventHandler eventHandler;
    private RoleApplier roleApplier;
    private RoleHider roleHider;
    @Setter
    private RoleChat roleChat;
    @Getter
    private RoleBoard roleboard;

    public Role(RoleName name,
                RoleEventHandler eventHandler,
                RoleApplier roleApplier,
                RoleChat roleChat,
                RoleHider roleHider,
                RoleBoard roleboard) {
        this.name = name;
        this.eventHandler = eventHandler;
        this.roleApplier = roleApplier;
        this.roleChat = roleChat;
        this.roleHider = roleHider;
        this.roleboard = roleboard;
    }

    public final void applyStuff(Player player) {
        this.roleApplier.apply(player);
    }

    public final void checkHide(Player player) {
        this.roleHider.check(player);
    }

    public final void end(Player player) {
        this.roleApplier.end(player);
    }

    public final void chat(Player player, String message) {
        this.roleChat.chat(player, message);
    }
}
