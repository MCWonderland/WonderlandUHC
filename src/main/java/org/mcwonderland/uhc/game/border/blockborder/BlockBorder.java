package org.mcwonderland.uhc.game.border.blockborder;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.mcwonderland.uhc.util.UniqueQueue;
import org.mcwonderland.uhc.util.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.*;
import java.util.stream.Collectors;

public class BlockBorder implements Listener {
    private static final int TRIGGER_RANGE = 3;

    private Map<UUID, Queue<Location>> recentChanges = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (isDifferentBlock(e.getFrom(), e.getTo()))
            checkBlockBorder(e.getPlayer(), e.getTo());
    }

    private boolean isDifferentBlock(Location from, Location to) {
        return from.getBlockX() != to.getBlockX()
                || from.getBlockY() != to.getBlockY()
                || from.getBlockZ() != to.getBlockZ();
    }

    private void checkBlockBorder(Player player, Location nextLocation) {
        if (nextLocation.getWorld() != UHCWorldUtils.getWorld())
            return;

        Queue<Location> recent = getRecent(player);

        if (distanceToBorder(nextLocation) > TRIGGER_RANGE) {
            for (Location location : recent)
                player.sendBlockChange(location, CompMaterial.AIR.getMaterial(), (byte) 0);
            return;
        }

        Set<Location> sphere = Cuboid.selectLocations(nextLocation, TRIGGER_RANGE)
                .stream()
                .filter(loc -> isBorderLocation(loc) && CompMaterial.isAir(loc.getBlock()))
                .collect(Collectors.toSet());

        UniqueQueue<Location> noChangeLocations = new UniqueQueue<>();

        while (!recent.isEmpty()) {
            Location location = recent.remove();

            if (sphere.remove(location))
                noChangeLocations.add(location);
            else
                player.sendBlockChange(location, CompMaterial.AIR.getMaterial(), (byte) 0);
        }

        recent.addAll(noChangeLocations);

        for (Location location : sphere) {
            player.sendBlockChange(location, CompMaterial.BEDROCK.getMaterial(), (byte) 0);
            recent.add(location);
        }
    }

    private int distanceToBorder(Location location) {
        int borderRadius = Game.getGame().getCurrentBorder() / 2;

        int xDis = borderRadius - Math.abs(location.getBlockX());
        int zDis = borderRadius - Math.abs(location.getBlockZ());

        return Math.min(xDis, zDis);
    }

    private Queue<Location> getRecent(Player player) {
        Queue<Location> recent = recentChanges.get(player.getUniqueId());

        if (recent == null) {
            recent = new UniqueQueue<>();
            recentChanges.put(player.getUniqueId(), recent);
        }

        return recent;
    }

    private boolean isBorderLocation(Location loc) {
        return isBorderLocation(loc.getBlockZ()) || isBorderLocation(loc.getBlockX());
    }

    private boolean isBorderLocation(int i) {
        return Math.abs(i) == Game.getGame().getCurrentBorder() / 2;
    }

}
