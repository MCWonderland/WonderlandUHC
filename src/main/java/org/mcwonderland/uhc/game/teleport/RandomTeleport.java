package org.mcwonderland.uhc.game.teleport;

import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Location;

public class RandomTeleport extends TeleportMode {

    @Override
    protected Location getNextLocation() {
        return getPoints().poll();
    }

    @Override
    protected void generateFallback(int border) {
        generateLocationsCache(border, 1);
    }

    @Override
    public void preGenerateTeleportLocation(int border) {
        generateLocationsCache(border, Extra.getOnlinePlayers() / 5);
    }
}
