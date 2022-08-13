package org.mcwonderland.uhc.game.teleport;

import lombok.AccessLevel;
import lombok.Getter;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.model.Teleporter;
import org.mcwonderland.uhc.util.BorderUtil;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.Location;

import java.util.LinkedList;
import java.util.Queue;

public abstract class TeleportMode {

    @Getter(AccessLevel.MODULE)
    private final Queue<Location> points = new LinkedList<>();

    public abstract void preGenerateTeleportLocation(int border);

    protected abstract Location getNextLocation();

    protected abstract void generateFallback(int border);

    public final void teleport(UHCTeam team) {
        Teleporter.teleportTeamTo(team, getSafeLocation());
        onTelepoort(team);
    }

    public final void clearLocationCache() {
        points.clear();
    }

    protected void onTelepoort(UHCTeam team) {

    }

    protected final void generateLocationsCache(int border, int amount) {
        for (int i = 0; i < amount; i++)
            points.add(Teleporter.getRandomTp(UHCWorldUtils.getWorld(), border));
    }

    private Location getSafeLocation() {
        Location location;
        do {
            if (points.isEmpty())
                generateFallback(Game.getGame().getCurrentBorder());

            location = getNextLocation();

        } while (!BorderUtil.isInBorder(location));

        return location;
    }
}
