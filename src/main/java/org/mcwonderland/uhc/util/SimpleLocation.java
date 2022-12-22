package org.mcwonderland.uhc.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

@Getter
@Setter
public class SimpleLocation implements ConfigSerializable {
    private Double x, y, z;
    private Float yaw, pitch;
    private World world;

    @Override
    public SerializedMap serialize() {
        SerializedMap map = new SerializedMap();

        map.put("X", x);
        map.put("Y", y);
        map.put("Z", z);
        map.put("Yaw", yaw);
        map.put("Pitch", pitch);
        map.put("World", world.getName());

        return map;
    }

    public static SimpleLocation deserialize(SerializedMap map) {
        SimpleLocation simpleLocation = new SimpleLocation();

        simpleLocation.x = map.getDouble("X");
        simpleLocation.y = map.getDouble("Y");
        simpleLocation.z = map.getDouble("Z");
        simpleLocation.yaw = map.getFloat("Yaw");
        simpleLocation.pitch = map.getFloat("Pitch");
        simpleLocation.world = Bukkit.getWorld(map.getString("World"));

        return simpleLocation;
    }

    public static SimpleLocation fromLocation(Location location) {
        SimpleLocation info = new SimpleLocation();

        info.x = location.getX();
        info.y = location.getY();
        info.z = location.getZ();
        info.yaw = location.getYaw();
        info.pitch = location.getPitch();
        info.world = location.getWorld();

        return info;
    }

    public Location toLocation() {
        return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch);
    }
}
