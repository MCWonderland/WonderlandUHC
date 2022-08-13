package org.mcwonderland.uhc.game.state.starting;

import org.mcwonderland.uhc.game.state.share.join.ClearBehavior;
import org.mcwonderland.uhc.game.state.share.join.JoinListener;
import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;

public class StartingJoinListener extends JoinListener {

    public StartingJoinListener() {
        super(
                new ClearBehavior()
        );
    }


    @Override
    protected void onPlayerJoin(UHCJoinEvent e) {
        e.getUhcPlayer().getEventHandler().onStartingJoin(e);
    }
}
