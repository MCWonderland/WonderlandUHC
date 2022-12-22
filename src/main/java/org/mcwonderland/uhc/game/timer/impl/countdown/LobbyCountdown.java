package org.mcwonderland.uhc.game.timer.impl.countdown;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.timer.Countdown;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.*;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.util.*;
import org.mineacademy.fo.remain.Remain;

public class LobbyCountdown extends Countdown {

    @Override
    public void execute() {
        Game game = Game.getGame();
        UHCGameSettings settings = game.getSettings();

        BorderUtil.setInitialBorders();
        game.setCurrentBorder(settings.getBorderSettings().getInitialBorder());
        UHCWorldUtils.getWorld().setDifficulty(Difficulty.PEACEFUL);
        TeamModifier.splitTeams();

        for (Player p : Remain.getOnlinePlayers()) {
            p.closeInventory();

            if (GameUtils.isStaff(p))
                p.teleport(UHCWorldUtils.getZeroZero());
            else
                Extra.comepleteClear(p);
        }

        game.nextState();
    }

    @Override
    public int getToggleTimer() {
        return Settings.Game.PRE_START_TIME;
    }

    @Override
    public String getCountdownBroadcast() {
        Extra.sound(Sounds.Countdown.Lobby.TICK);
        return Messages.CountDown.SCATTER_ANNOUNCE;
    }

    @Override
    public String getToggledBroadcast() {
        Extra.sound(Sounds.Countdown.Lobby.RUN);
        return Messages.CountDown.SCATTER_STARTED;
    }
}
