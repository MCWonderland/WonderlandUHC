package org.mcwonderland.uhc.game.state.share.join;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.mineacademy.fo.model.SimpleReplacer;

public class DefaultJoinMessage implements JoinBehavior {

    @Override
    public void onJoin(UHCJoinEvent e) {

        Player player = e.getPlayer();
        Game game = e.getGame();

        if (CacheSaver.getLoadingStatus() == LoadingStatus.DONE) {
            Chat.send(player, new SimpleReplacer(Messages.Lobby.WELCOME_MSG)
                    .replace("{player}", player.getName())
                    .replace("{host}", game.getHost())
                    .replace("{title}", game.getSettings().getTitle())
                    .toArray());

            Chat.broadcast(Messages.Lobby.PLAYER_JOIN_MSG
                    .replace("{player}", player.getName())
                    .replace("{online}", "" + Extra.getOnlinePlayers())
                    .replace("{max}", "" + Game.getSettings().getMaxPlayers()));
        } else
            Chat.send(player, new SimpleReplacer(Messages.Lobby.WELCOME_MSG_CONFIGURING)
                    .replace("{cmd}", "uhc tutorial config")
                    .getMessages());
    }
}
