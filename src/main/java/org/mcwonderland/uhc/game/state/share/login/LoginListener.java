package org.mcwonderland.uhc.game.state.share.login;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.game.state.share.login.checker.LoginChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public abstract class LoginListener implements Listener {

    private UHCLoginEvent event;
    private List<LoginChecker> checkers;

    public LoginListener(LoginChecker... checkers) {
        this.checkers = Lists.newArrayList(checkers);
    }

    @EventHandler
    public final void onLogin(PlayerLoginEvent e) {
        UHCLoginEvent loginEvent = new UHCLoginEvent(e);

        boolean pass = true;

        for (LoginChecker checker : checkers) {
            checker.check(loginEvent);

            if (loginEvent.getResult() != PlayerLoginEvent.Result.ALLOWED) {
                pass = false;

                break;
            }
        }

        if (pass)
            onPassAllChecks(loginEvent);
    }

    protected void onPassAllChecks(UHCLoginEvent e) {

    }
}
