package org.mcwonderland.uhc.game.timer.impl.countdown;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.api.event.timer.UHCStartedEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameManager;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.timer.Countdown;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.*;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.util.*;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleReplacer;

public class StartCountdown extends Countdown {

    @Override
    public void execute() {
        Game game = Game.getGame();
        UHCGameSettings settings = game.getSettings();
        game.setAllPlayers(UHCPlayers.countOf(uhcPlayer -> uhcPlayer.getRoleName() == RoleName.PLAYER));
        game.nextState();
        setupWorlds();

        for (UHCPlayer uhcPlayer : UHCPlayer.getAllPlayers()) {
            Player player = uhcPlayer.getPlayer();

            if (uhcPlayer.isOnline()) {
                GameManager.unFreeze(player);
                if (GameUtils.isGamingPlayer(player)) {
                    player.setGameMode(GameMode.SURVIVAL);
                    Extra.comepleteClear(player);
                    InventorySaver.setContents(player, InventorySaver.SaveType.CUSTOM_INVENTORY);
                    if (settings.getInitialExperience() > 0)
                        player.setLevel(settings.getInitialExperience());
                }
            } else {
                if (uhcPlayer.getRoleName() == RoleName.PLAYER)
                    uhcPlayer.changeSpectatorRole();
            }
        }

        Common.callEvent(new UHCStartedEvent());
    }

    private static void setupWorlds() {
        for (World world : UHCWorldUtils.getUhcWorlds()) {
            if (Settings.Misc.ALWAYS_DAY)
                world.setGameRuleValue("doDaylightCycle", "false");
            if (Settings.Misc.NO_FIRE_TICK)
                world.setGameRuleValue("doFireTick", "false");

            world.setSpawnLocation(0, 0, 0);
            world.setTime(Settings.Misc.ALWAYS_DAY ? 6000 : 0);
            world.setDifficulty(Difficulty.HARD);
        }
    }

    @Override
    public int getToggleTimer() {
        return Settings.Game.TIME_TO_START_AFTER_TELEPORT;
    }

    @Override
    public String getCountdownBroadcast() {
        Extra.sound(Sounds.Countdown.Start.TICK);
        return Messages.CountDown.STARTING_ANNOUNCE;
    }

    @Override
    public String getToggledBroadcast() {
        Chat.broadcast(new SimpleReplacer(Messages.CountDown.GAME_STARTED)
                .replace("{host}", Game.getGame().getHost())
                .toArray());
        Extra.sound(Sounds.Countdown.Start.RUN);
        return null;
    }
}
