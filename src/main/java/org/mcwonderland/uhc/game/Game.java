package org.mcwonderland.uhc.game;

import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.api.event.GameChangeSettingsEvent;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.state.GameState;
import org.mcwonderland.uhc.game.state.playing.PlayingState;
import org.mcwonderland.uhc.game.state.preparing.PreparingState;
import org.mcwonderland.uhc.game.state.starting.PreStartState;
import org.mcwonderland.uhc.game.state.starting.TeleportingState;
import org.mcwonderland.uhc.model.InvinciblePlayer;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.PlayerCollection;

import java.util.*;


@Getter
@Setter
public class Game {
    @Getter
    private static Game game = new Game();

    private UHCGameSettings settings = UHCGameSettings.defaultSettings();
    private String host = "";
    private int allPlayers;
    private int currentBorder = 0;
    private boolean centerCleaner;
    private boolean damageEnabled, finalHealEnabled, pvpEnabled;

    private PlayerCollection whiteList = new PlayerCollection();
    private Map<UUID, InvinciblePlayer> invinciblePlayers = new HashMap<>();

    //new
    private Queue<GameState> states = new LinkedList<>();
    private GameState currentState;

    private Game() {
        this.states.add(new PreparingState(StateName.WAITING));
        this.states.add(new TeleportingState(StateName.TELEPORTING));
        this.states.add(new PreStartState(StateName.PRE_START));
        this.states.add(new PlayingState(StateName.PLAYING));

        this.currentState = this.states.remove();
        this.currentState.init();
    }

    public static UHCGameSettings getSettings() {
        return game.settings;
    }

    public static void changeSettings(UHCGameSettings newSettings) {
        game.settings = newSettings;

        Common.callEvent(new GameChangeSettingsEvent(newSettings));
    }

    public void nextState() {
        this.currentState.end();
        this.currentState = states.remove();
        this.currentState.init();

        GameTimerRunnable.totalSecond = 0;
        GameTimerRunnable.tick = 0;

        this.currentState.start();
    }

    public void tryToStart(CommandSender sender) {
        if (getCurrentStateName() != StateName.WAITING) {
            Chat.send(sender, Messages.Host.GAME_RUNNING);
            return;
        }

        GameTimerRunnable.RUN = true;
        Extra.sound(Sounds.Countdown.Lobby.START);
    }

    public StateName getCurrentStateName() {
        return this.currentState.getName();
    }
}
