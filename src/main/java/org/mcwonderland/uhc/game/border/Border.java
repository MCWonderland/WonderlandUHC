package org.mcwonderland.uhc.game.border;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCBorderSettings;

public interface Border {

    void shrinkToNextSize();

    void onCommand(int size);

    void onCountdown(int untilShrink);

    String getCountdownBroadcast();

    String getShrinkMessage();

    default UHCBorderSettings getBorderSettings() {
        return Game.getSettings().getBorderSettings();
    }
}
