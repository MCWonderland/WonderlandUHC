package org.mcwonderland.uhc.game.teleport;

import lombok.Getter;

/**
 * 2019-12-11 下午 08:11
 */
public enum TeleportType {
    POINT(new PointTeleport()),
    RANDOM(new RandomTeleport());

    @Getter
    private TeleportMode mode;

    TeleportType(TeleportMode mode) {
        this.mode = mode;
    }
}