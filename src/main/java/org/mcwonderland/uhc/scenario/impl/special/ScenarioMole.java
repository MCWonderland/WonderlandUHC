package org.mcwonderland.uhc.scenario.impl.special;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.model.SimpleSound;

import java.util.HashSet;
import java.util.Set;

public class ScenarioMole extends ConfigBasedScenario implements Listener {

    @FilePath(name = "Mole_Spawn_Minutes")
    private Integer moleSpawnMinutes;

    @FilePath(name = "Mole_Countdown_Sound")
    private SimpleSound moleSpawnCountdown;

    @FilePath(name = "Mole_Spawn_Sound")
    private SimpleSound moleSpawnSound;

    private static Set<UHCPlayer> MolePlayers = new HashSet<>();

    public ScenarioMole(ScenarioName name) {
        super(name);
    }

    //todo 這我不知道怎麼寫
    @Override
    protected void onConfigReload() {

    }

    public static Set<UHCPlayer> getMoleList() {
        return Sets.newHashSet(MolePlayers);

    }

    // 是否為間諜
    public boolean isMole() {
        return false;

    }

    @RequiredArgsConstructor
    public class MoleSpawn {

        private final UHCPlayer mole;

        private final Location location;

        private int time;
        public void tick() {
            time++;
            int secondUntilSpawn = moleSpawnMinutes * 60 + 5;

            if(secondUntilSpawn <= 0) {
                moleSpawn();
                moleSpawnSound.play(location);
            } else if (Extra.isBetween(secondUntilSpawn, 1, 5)) {
                moleSpawnCountdown.play(location);
            }
        }

        public void moleSpawn() {}


    }


}
