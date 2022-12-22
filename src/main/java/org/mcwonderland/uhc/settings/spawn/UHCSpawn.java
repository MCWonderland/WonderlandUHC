package org.mcwonderland.uhc.settings.spawn;

import lombok.AccessLevel;
import lombok.Getter;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.exception.FoException;
import org.mineacademy.fo.settings.YamlConfig;

@Getter
public class UHCSpawn extends YamlConfig {

    @Getter(AccessLevel.PRIVATE)
    private final String prefix;
    private Location location;
    private boolean set;

    protected UHCSpawn(String prefix) {
        loadConfiguration(UHCFiles.SPAWNS);

        this.prefix = prefix;
        this.location = getLocationSafe(prefix);
    }

    private Location getLocationSafe(String path) {
        try {
            Location location = getLocation(path);
            set = true;
            return location;
        } catch (FoException | NullPointerException e) {
            return defaultLocation();
        }
    }

    private Location defaultLocation(String... msg) {
        Common.log(msg);
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    public void updateLocation(Location newOne) {
        location = newOne;
        set = true;

        save(prefix, newOne);
    }

}
