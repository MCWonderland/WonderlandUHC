package org.mcwonderland.uhc.game.state.preparing;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.game.state.share.login.LoginListener;
import org.mcwonderland.uhc.game.state.share.login.UHCLoginEvent;
import org.mcwonderland.uhc.game.state.share.login.checker.LoginChecker;
import org.mcwonderland.uhc.game.state.share.login.checker.WhitelistChecker;
import org.mcwonderland.uhc.settings.Messages;
import org.mineacademy.fo.remain.Remain;

public class PreparingLoginListener extends LoginListener {

    public PreparingLoginListener() {
        super(
                new WhitelistChecker(),
                new GeneratingChecker(),
                new ConfigChecker(),
                new FullChecker()
        );
    }

    private static class GeneratingChecker extends LoginChecker {
        @Override
        protected void checkLogin(UHCLoginEvent e) {
            if (CacheSaver.getLoadingStatus() == LoadingStatus.GENERATING)
                disallow(Messages.Kick.GENERATING);
        }
    }

    private static class ConfigChecker extends LoginChecker {

        @Override
        protected void checkLogin(UHCLoginEvent e) {
            LoadingStatus loadingStatus = CacheSaver.getLoadingStatus();

            if (loadingStatus == LoadingStatus.CONFIGURING
                    && !UHCPermission.BYPASS_JOIN_CONFIGURING.hasPerm(e.getPlayer()))
                disallow(Messages.Kick.WAITING_HOST);
        }
    }

    private static class FullChecker extends LoginChecker {

        @Override
        protected void checkLogin(UHCLoginEvent e) {

            if (CacheSaver.getLoadingStatus() == LoadingStatus.DONE) {
                boolean full = Remain.getOnlinePlayers().size() >= Game.getSettings().getMaxPlayers();

                if (full && !UHCPermission.BYPASS_JOIN_CONFIGURING.hasPerm(e.getPlayer()))
                    disallow(Messages.Kick.FULL);
            }
        }
    }
}
