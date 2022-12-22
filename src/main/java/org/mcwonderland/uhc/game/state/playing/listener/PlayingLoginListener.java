package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.state.share.login.LoginListener;
import org.mcwonderland.uhc.game.state.share.login.UHCLoginEvent;
import org.mcwonderland.uhc.game.state.share.login.checker.LoginChecker;
import org.mcwonderland.uhc.game.state.share.login.checker.WhitelistChecker;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;

public class PlayingLoginListener extends LoginListener {

    public PlayingLoginListener() {
        super(
                new WhitelistChecker(),
                new BypassGameStartedChecker()
        );
    }

    private static class BypassGameStartedChecker extends LoginChecker {

        @Override
        protected void checkLogin(UHCLoginEvent e) {
            Player player = e.getPlayer();

            if (!GameUtils.isGamingPlayer(player)
                    && !UHCPermission.BYPASS_JOIN_STARTED.hasPerm(player))
                disallow(Messages.Kick.GAME_STARTED);
        }
    }
}
