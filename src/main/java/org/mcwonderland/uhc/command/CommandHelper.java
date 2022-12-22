package org.mcwonderland.uhc.command;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.exception.CommandException;
import org.mineacademy.fo.settings.SimpleLocalization;

@UtilityClass
public class CommandHelper {

    public UHCPlayer findUHCPlayer(String name) {
        Player player = PlayerUtil.getPlayerByNick(name, false);

        if (player == null)
            throw new CommandException(SimpleLocalization.Player.NOT_ONLINE.replace("{player}", name));

        return UHCPlayer.getUHCPlayer(player);
    }

    public void checkGameStarted() {
        if (!GameUtils.isGameStarted())
            throw new CommandException(Messages.NOT_YET_STARTED);
    }

    public void checkWaiting() {
        if (!GameUtils.isWaiting())
            throw new CommandException(Messages.USE_ONLY_WHILE_WAITING);
    }

    public void checkGamingPlayer(Player player) {
        if (!GameUtils.isGamingPlayer(player))
            throw new CommandException(Messages.NOT_GAMING_PLAYER);
    }

    public void checkSpectator(Player player) {
        if (GameUtils.isGamingPlayer(player))
            throw new CommandException(Messages.ONLY_FOR_SPECTATOR);
    }

    public void checkExecuteSelf(Player execute, Player target) {
        if (execute == target)
            throw new CommandException(CommandSettings.CANT_EXECUTE_SELF);
    }
}
