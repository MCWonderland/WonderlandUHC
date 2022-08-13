package org.mcwonderland.uhc.command.impl.game;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.SpectateMode;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-24 下午 12:50
 */
public class SpecToggleCommand extends SimpleCommand {

    public SpecToggleCommand(String label) {
        super(label);
    }

    @Override
    protected void onCommand() {
        Player player = getPlayer();

        CommandHelper.checkGameStarted();
        CommandHelper.checkSpectator(player);
        checkBoolean(Settings.Spectator.SPECTATE_MODE == SpectateMode.DEFAULT,
                CommandSettings.Uhc.SpecToggle.ONLY_FOR_DEFAULT_SPECTATE_MODE);

        GameMode gameMode = player.getGameMode();
        player.setGameMode(gameMode == GameMode.CREATIVE ? GameMode.SPECTATOR : GameMode.CREATIVE);

        tell(CommandSettings.Uhc.SpecToggle.GAMEMODE_TOGGLED.replace("{cmd}", getLabel()));
        Extra.sound(player, Sounds.Commands.SPEC_TOGGLE);
    }

}
