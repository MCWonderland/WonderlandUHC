package org.mcwonderland.uhc.game.state.preparing;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.state.share.LobbyQuitListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PreparingQuitListener extends LobbyQuitListener {


    @Override
    public void onPlayerQuit(PlayerQuitEvent e) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(e.getPlayer());
        UHCTeam team = uhcPlayer.getTeam();

        if (team != null)
            team.leave(uhcPlayer);

        super.onPlayerQuit(e);
    }

}
