package org.mcwonderland.uhc.game.timer.impl.countdown;

import org.mcwonderland.uhc.api.event.timer.PvPEnableEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.timer.Countdown;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.Common;

public class PvpCountdown extends Countdown {

    @Override
    public void execute() {
        Common.callEvent(new PvPEnableEvent());
        Game.getGame().setPvpEnabled(true);
    }

    @Override
    public int getToggleTimer() {
        return getTimerSettings().getPvpTime();
    }

    @Override
    public String getCountdownBroadcast() {
        Extra.sound(Sounds.Countdown.Pvp.TICK);
        return Messages.CountDown.PVP_ANNOUNCE;
    }

    @Override
    public String getToggledBroadcast() {
        Extra.sound(Sounds.Countdown.Pvp.RUN);
        return Messages.CountDown.PVP_ENABLED;
    }
}
