package org.mcwonderland.uhc.game.state.preparing;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.state.share.join.ClearBehavior;
import org.mcwonderland.uhc.game.state.share.join.JoinListener;
import org.mcwonderland.uhc.game.state.share.join.UHCJoinEvent;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.tools.Hotbars;
import org.mcwonderland.uhc.util.PlayerHider;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PreparingJoinListener extends JoinListener {

    public PreparingJoinListener() {
        super(
                new ClearBehavior()
        );
    }

    @Override
    protected void onPlayerJoin(UHCJoinEvent e) {
        Player player = e.getPlayer();
        Game game = e.getGame();

        player.teleport(UHCWorldUtils.getLobbySpawn());

        Hotbars.giveLobbyItems(player);

        player.setGameMode(GameMode.ADVENTURE);

        if (Settings.Misc.LOBBY_HIDE)
            PlayerHider.hideAll(player);

        if (game.getHost().isEmpty())
            game.setHost(player.getName());
    }
}
