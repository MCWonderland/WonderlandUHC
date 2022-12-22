package org.mcwonderland.uhc.game.state.share.login.checker;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.state.share.login.UHCLoginEvent;
import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.entity.Player;

public class WhitelistChecker extends LoginChecker {

    @Override
    protected void checkLogin(UHCLoginEvent e) {
        Player player = e.getPlayer();

        boolean whitelistOn = e.getGame().getSettings().isWhitelistOn();
        boolean whiteListed = e.getGame().getWhiteList().contains(player);
        boolean hasPerm = UHCPermission.BYPASS_JOIN_WHITELIST.hasPerm(player);

        if (whitelistOn && (!whiteListed && !hasPerm)) {
            disallow(Messages.Kick.WHITELISTED);
        }
    }
}
