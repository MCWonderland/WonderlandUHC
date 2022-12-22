package org.mcwonderland.uhc.game.player.role.spectator;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.player.role.models.RoleChat;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;
import org.mineacademy.fo.remain.Remain;

import java.util.Collection;

public class SpectatorChat extends RoleChat {

    @Override
    public void chat(Player player, String message) {
        String formattedMsg = replace(Messages.ChatFormat.SPECTATORS, player, message);

        if (UHCPermission.SPECTATOR_CHAT_GLOBAL.hasPerm(player)) {
            for (Player o : Remain.getOnlinePlayers())
                Chat.send(o, formattedMsg);
        } else {
            Collection<UHCPlayer> toSend = UHCPlayers
                    .getOnline(uhcPlayer -> uhcPlayer.isDead()
                            || UHCPermission.SEE_SPECTATOR_CHAT.hasPerm(uhcPlayer.getPlayer()));

            toSend.forEach(uhcPlayer -> Chat.send(uhcPlayer.getPlayer(), formattedMsg));
        }
    }
}
