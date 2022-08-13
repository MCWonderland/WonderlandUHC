package org.mcwonderland.uhc.api;

import org.mcwonderland.uhc.game.player.staff.OreAlert;

public interface StaffOptions {
    void toggleOreAlert(OreAlert alert);

    boolean hasOreAlert(OreAlert alert);

    float getMCSpeed();

    void setSpeed(int speed);

    void setShowSpectator(boolean showSpectator);

    void setShowStaff(boolean showStaff);

    int getSpeed();

    boolean isShowSpectator();

    boolean isShowStaff();
}
