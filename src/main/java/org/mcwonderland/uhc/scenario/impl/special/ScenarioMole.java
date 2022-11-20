package org.mcwonderland.uhc.scenario.impl.special;

import com.google.common.collect.Sets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mcwonderland.uhc.events.UHCGameTimerUpdateEvent;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.model.SimpleSound;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ScenarioMole extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Mole_Spawn_Minutes")
    private Integer moleSpawnMinutes;

    @FilePath(name = "Mole_Countdown_Sound")
    private SimpleSound moleSpawnCountdownSound;

    @FilePath(name = "Mole_Spawn_Sound")
    private SimpleSound moleSpawnSound;

    @FilePath(name = "Mole_Countdown_Message")
    private String moleCountdownMessage;

    @FilePath(name = "Mole_Player_Message")
    private String molePlayerMessage;

    @FilePath(name = "NotMole_Player_Message")
    private String notMolePlayerMessage;

    private Integer moleSpawnSeconds;

    private static Set<UHCPlayer> molePlayers = new HashSet<>();

    public ScenarioMole(ScenarioName name) {
        super(name);
    }

    @Override
    protected void onConfigReload() {
        moleSpawnSeconds = moleSpawnMinutes * 60 + 5;
    }

    @EventHandler
    public void setMoleSpawnCountdown(UHCGameTimerUpdateEvent event) {
        int currentSecond = event.getCurrentSecond();
        int secondToSpawn = moleSpawnSeconds - currentSecond;

        if (secondToSpawn == 0) {
            doMoleSpawn();
            sendMoleSpawnMessage();
            Extra.sound(moleSpawnSound);

        } else if (Extra.isBetween(secondToSpawn, 1, 5)) {
            Extra.sound(moleSpawnCountdownSound);
            Chat.broadcast(moleCountdownMessage.replace("{second}", "" + secondToSpawn));
        }
    }

    public void doMoleSpawn() {
        for (UHCTeam team : UHCTeam.getAliveTeams()) {
            Object[] teamPlayers = team.getPlayers().toArray();

            Random rndm = new Random();
            int rndmNumber = rndm.nextInt(team.getPlayersAmount());

            molePlayers.add((UHCPlayer) teamPlayers[rndmNumber]);
        }
    }

    public void sendMoleSpawnMessage() {
        for (UHCPlayer player : UHCPlayer.getAllPlayers()) {
            if (molePlayers.contains(player)) {
                Chat.broadcast(molePlayerMessage);
            } else if (!molePlayers.contains(molePlayerMessage)) {
                Chat.broadcast(notMolePlayerMessage);
            }
        }
    }

    public static Set<UHCPlayer> getMoleList() {
        return Sets.newHashSet(molePlayers);
    }

    public boolean isMole(UHCPlayer player) {
        if (molePlayers.contains(player)) {
            return true;
        } else {
            return false;
        }
    }


}



