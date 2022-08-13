package org.mcwonderland.uhc.settings.spawn;

import lombok.Getter;

public class Spawns {

    @Getter
    private static UHCSpawn lobbySpawn;


    public static void reload() {
        lobbySpawn = new UHCSpawn("Lobby");
    }

}
