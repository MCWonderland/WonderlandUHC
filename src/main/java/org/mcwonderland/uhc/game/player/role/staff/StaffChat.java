package org.mcwonderland.uhc.game.player.role.staff;

import org.mcwonderland.uhc.game.player.role.models.RoleChat;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;

public class StaffChat extends RoleChat {

    @Override
    public void chat(Player player, String message) {
        Chat.broadcast(replace(Messages.ChatFormat.STAFF, player, message));
    }

}
