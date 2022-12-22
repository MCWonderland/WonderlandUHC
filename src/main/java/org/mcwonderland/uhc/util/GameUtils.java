package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.border.BorderType;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.mineacademy.fo.MathUtil;

@UtilityClass
public class GameUtils {

    public boolean isGamingPlayer(Entity entity) {
        UHCPlayer uhcPlayer = UHCPlayer.getFromEntity(entity);

        return uhcPlayer != null && uhcPlayer.getRoleName() == RoleName.PLAYER;
    }

    public boolean isGamingPlayer(Player player) {
        return UHCPlayer.getUHCPlayer(player).getRoleName() == RoleName.PLAYER;
    }

    public boolean isStaff(Player player) {
        return UHCPlayer.getUHCPlayer(player).getRoleName() == RoleName.STAFF;
    }

    public int getTeamSize() {
        return Game.getSettings().getTeamSettings().getTeamSize();
    }

    public boolean isTeamMode() {
        return getTeamSize() > 1;
    }

    public boolean isGameStarted() {
        return Game.getGame().getCurrentStateName().isStarted();
    }

    public boolean isScatting() {
        return Game.getGame().getCurrentStateName().isTeleport();
    }

    public boolean isWaiting() {
        return Game.getGame().getCurrentStateName().isLobby();
    }

    public boolean isNetherOn() {
        return Game.getSettings().isUsingNether() && Game.getGame().isPvpEnabled();
    }

    public BorderType getBorderType() {
        return Game.getSettings().getBorderSettings().getBorderType();
    }

    public int getCurrentNetherBorder() {
        return MathUtil.range(BorderUtil.getMoveBorder(UHCWorldUtils.getNether()), 0, Game.getSettings().getBorderSettings().getInitialNetherBorder());
    }

    public void spectateTeleport(Player spectator, Player target) {
        spectator.teleport(target);
        Chat.send(spectator, Messages.Spectator.TELEPORTED_TO_PLAYER
                .replace("{player}", target.getName()));
    }

    public void sendMessages(RoleName status, String... msg) {
        UHCPlayers.getOnline(uhcPlayer -> uhcPlayer.getRoleName() == status)
                .forEach(uhcPlayer -> Chat.send(uhcPlayer.getPlayer(), msg));
    }
}
