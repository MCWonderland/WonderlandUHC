package org.mcwonderland.uhc.game.timer.impl.countdown;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.border.Border;
import org.mcwonderland.uhc.game.timer.Countdown;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;

public class BorderCountdown extends Countdown {

    @Override
    public void execute() {
        getBorderMode().shrinkToNextSize();
    }

    @Override
    public void onCountdown(int untilEnable) {
        getBorderMode().onCountdown(untilEnable);
    }

    @Override
    public int getToggleTimer() {
        return getTimerSettings().getBorderShrinkTime();
    }

    @Override
    public String getCountdownBroadcast() {
        Extra.sound(Sounds.Countdown.Border.TICK);
        return getBorderMode().getCountdownBroadcast();
    }

    @Override
    public String getToggledBroadcast() {
        Extra.sound(Sounds.Countdown.Border.RUN);
        return getBorderMode().getShrinkMessage();
    }

    private Border getBorderMode() {
        return Game.getSettings().getBorderSettings().getBorderType().getMode();
    }
}
