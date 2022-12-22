package org.mcwonderland.uhc.game.state.share.login;

import lombok.Getter;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class UHCLoginEvent extends PlayerLoginEvent {

    private PlayerLoginEvent source;
    @Getter
    private UHCPlayer uhcPlayer;
    @Getter
    private Game game;

    public UHCLoginEvent(PlayerLoginEvent source) {
        super(
                source.getPlayer(),
                source.getHostname(),
                source.getAddress(),
                source.getResult(),
                source.getHostname(),
                source.getRealAddress()
        );
        this.source = source;
        this.uhcPlayer = UHCPlayer.getUHCPlayer(source.getPlayer());
        this.game = Game.getGame();
    }

    @Override
    public void setResult(@NotNull PlayerLoginEvent.Result result) {
        source.setResult(result);
    }

    @Override
    public void setKickMessage(@NotNull String message) {
        source.setKickMessage(message);
    }

    @Override
    public void disallow(@NotNull PlayerLoginEvent.Result result, @NotNull String message) {
        source.disallow(result, message);
    }
}
