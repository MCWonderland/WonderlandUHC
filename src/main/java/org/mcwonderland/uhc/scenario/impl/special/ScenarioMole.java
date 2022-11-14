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
import java.util.Set;

public class ScenarioMole extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Mole_Spawn_Minutes")
    private Integer moleSpawnMinutes;

    @FilePath(name = "Mole_Countdown_Sound")
    private SimpleSound moleSpawnCountdownSound;

    @FilePath(name = "Mole_Spawn_Sound")
    private SimpleSound moleSpawnSound;

    private Integer moleSpawnSeconds = moleSpawnMinutes * 60 + 5;

    private static Set<UHCPlayer> molePlayers = new HashSet<>();

    public ScenarioMole(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void setMoleSpawnCountdown(UHCGameTimerUpdateEvent event) {
        int currentSecond = event.getCurrentSecond();
        int secondToSpawn = moleSpawnSeconds - currentSecond;

        if (secondToSpawn <= 0) {
            doMoleSpawn();
            Extra.sound(moleSpawnSound);
        } else if (Extra.isBetween(secondToSpawn, 1, 5)) {
            Extra.sound(moleSpawnCountdownSound);
            Chat.broadcast("&8[&c&l間諜模式&8] 間諜將在 " + secondToSpawn + " 秒後產生");
        }
    }

    public void doMoleSpawn() {
        // 每個隊伍中選出一位間諜
        // 邏輯 1：每個隊伍中隨機選出一個間諜
        // 邏輯 2：所有玩家中隨機選出 n 個間諜，間諜的隊伍不能重複
        // 判斷：玩家是否存活？玩家是否跟其他玩家隊伍相同？

        Set<UHCTeam> aliveTeams = UHCTeam.getAliveTeams();

        Set<UHCPlayer> players = UHCPlayer.getAllPlayers();

    }
    public static Set<UHCPlayer> getMoleList() {
        return Sets.newHashSet(molePlayers);
    }

    public boolean isMole(UHCPlayer player) {
        if (player == molePlayers) {
            return true;
        } else {
            return false;
        }
    }



}



