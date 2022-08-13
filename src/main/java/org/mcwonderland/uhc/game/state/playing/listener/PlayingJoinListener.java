package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.state.share.join.ClearBehavior;
import org.mcwonderland.uhc.game.state.share.join.JoinListener;
import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;

public class PlayingJoinListener extends JoinListener {

    public PlayingJoinListener() {
        super(
                new ClearBehavior()
        );
    }

    @Override
    protected void onPlayerJoin(UHCJoinEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();
        Player player = e.getPlayer();

        UHCTeam.createTeamIfNotExist(uhcPlayer);
        uhcPlayer.applyRoleStuff();
        uhcPlayer.getEventHandler().onGamingJoin(e);

        Chat.broadcast(Messages.Game.PLAYER_RECONNECT.replace("{player}", uhcPlayer.getTeam().getChatFormat() + player.getName()));
    }
}
