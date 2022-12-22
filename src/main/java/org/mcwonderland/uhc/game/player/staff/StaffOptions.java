package org.mcwonderland.uhc.game.player.staff;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
public class StaffOptions implements org.mcwonderland.uhc.api.StaffOptions {

    private int speed = 1;
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private HashSet<OreAlert> toggledOreAlerts = new HashSet<>();
    private boolean showSpectator, showStaff = true;

    public StaffOptions() {
        setupAlertMap();
    }

    private void setupAlertMap() {
        toggledOreAlerts.add(OreAlert.DIAMOND_ORE);
        toggledOreAlerts.add(OreAlert.GOLD_ORE);
    }

    @Override
    public void toggleOreAlert(OreAlert alert) {
        if (hasOreAlert(alert))
            toggledOreAlerts.remove(alert);
        else
            toggledOreAlerts.add(alert);
    }

    @Override
    public boolean hasOreAlert(OreAlert alert) {
        return toggledOreAlerts.contains(alert);
    }

    @Override
    public float getMCSpeed() {
        return speed * 0.1F + 0.1F;
    }
}