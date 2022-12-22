package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.spawn.Spawns;
import org.mcwonderland.uhc.settings.spawn.UHCSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class UHCWorldUtils {

    public World getLobby() {
        return getLobbySpawn().getWorld();
    }

    public World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }

    public World getNether() {
        return Bukkit.getWorld(getNetherName());
    }

    public String getWorldName() {
        return Settings.Game.UHC_WORLD_NAME;
    }

    public String getNetherName() {
        return Settings.Game.UHC_WORLD_NAME + "_nether";
    }

    public World[] getUhcWorlds() {
        return new World[]{getWorld(), getNether()};
    }

    public String[] getUhcWorldNames() {
        return new String[]{getWorldName(), getNetherName()};
    }

    public Location getLobbySpawn() {
        UHCSpawn lobbySpawn = Spawns.getLobbySpawn();
        Location location = lobbySpawn.getLocation();

        if (!lobbySpawn.isSet())
            Chat.broadcast(Messages.Lobby.NON_SPAWN_SET.replace("{cmd}", "setspawn"));

        return location;
    }

    public Location getZeroZero() {
        return new Location(getWorld(), 0, 100, 0);
    }

}
