package org.mcwonderland.uhc.game.timer.impl;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.border.BorderType;
import org.mcwonderland.uhc.game.settings.sub.UHCBorderSettings;
import org.mcwonderland.uhc.game.timer.Timer;
import org.mcwonderland.uhc.util.BorderUtil;
import org.mcwonderland.uhc.util.UHCWorldUtils;

public class BorderSizeUpdater implements Timer {

    @Override
    public void run() {
        UHCBorderSettings settings = Game.getSettings().getBorderSettings();

        if (settings.getBorderType() == BorderType.MOVING) {
            Game.getGame().setCurrentBorder(BorderUtil.getMoveBorder(UHCWorldUtils.getWorld()));
        }
    }

    @Override
    public int runTick() {
        return 1;
    }
}
