package org.mcwonderland.uhc.game.state.share.join;

import org.mcwonderland.uhc.util.Extra;

public class ClearBehavior implements JoinBehavior {

    @Override
    public void onJoin(UHCJoinEvent e) {
        Extra.comepleteClear(e.getPlayer());
    }

}
