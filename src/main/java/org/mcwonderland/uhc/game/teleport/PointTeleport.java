package org.mcwonderland.uhc.game.teleport;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.model.InvinciblePlayer;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.BorderUtil;
import org.bukkit.Location;

public class PointTeleport extends TeleportMode {

    @Override
    public Location getNextLocation() {
        Location toTeleport = getPoints().poll();

        if (BorderUtil.isInBorder(toTeleport)) {
            getPoints().add(toTeleport);
        }

        return toTeleport;
    }

    @Override
    protected void generateFallback(int border) {
        preGenerateTeleportLocation(border);
    }

    @Override
    protected void onTelepoort(UHCTeam team) {
        team.getAlives().forEach(this::addInvincible);
    }

    private void addInvincible(UHCPlayer uhcPlayer) {
        InvinciblePlayer.addInvincible(uhcPlayer, Settings.Game.POINT_TP_INVINCIBLE_TIME);
    }

    @Override
    public void preGenerateTeleportLocation(int border) {
        if (getPoints().size() != Settings.Game.POINT_TP_LOCATIONS) {
            getPoints().clear();
            generateLocationsCache(border, Settings.Game.POINT_TP_LOCATIONS);
        }
    }
}
