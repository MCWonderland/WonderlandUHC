package org.mcwonderland.uhc.game.state.share;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyQuitListener extends QuitListener {

    @Override
    protected void onPlayerQuit(PlayerQuitEvent e) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(e.getPlayer());

        e.setQuitMessage(Messages.Lobby.PLAYER_LEAVE_MSG
                .replace("{player}", uhcPlayer.getName())
                .replace("{online}", "" + (Extra.getOnlinePlayers() - 1))
                .replace("{max}", "" + Game.getSettings().getMaxPlayers()));
    }

}
