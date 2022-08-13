package org.mcwonderland.uhc.game.state.share.join;

import lombok.Getter;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

public class UHCJoinEvent extends PlayerJoinEvent {

    private PlayerJoinEvent source;
    @Getter
    private UHCPlayer uhcPlayer;
    @Getter
    private Game game;

    public UHCJoinEvent(PlayerJoinEvent source) {
        super(source.getPlayer(), source.getJoinMessage());
        this.source = source;
        this.uhcPlayer = UHCPlayer.getUHCPlayer(source.getPlayer());
        this.game = Game.getGame();
    }

    @Override
    public void setJoinMessage(@Nullable String joinMessage) {
        source.setJoinMessage(joinMessage);
    }
}
