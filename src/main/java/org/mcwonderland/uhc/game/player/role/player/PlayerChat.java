package org.mcwonderland.uhc.game.player.role.player;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.role.models.RoleChat;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;

public class PlayerChat extends RoleChat {

    @Override
    public void chat(Player player, String message) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (uhcPlayer.isTeamChat())
            Common.dispatchCommandAsPlayer(player, "team chat " + message);
        else
            Chat.broadcast(replace(Messages.ChatFormat.PLAYER, player, message));
    }

}
