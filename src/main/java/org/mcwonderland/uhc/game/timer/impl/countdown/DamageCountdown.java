package org.mcwonderland.uhc.game.timer.impl.countdown;

import org.mcwonderland.uhc.api.event.timer.DamageEnableEvent;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.timer.Countdown;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.Common;

public class DamageCountdown extends Countdown {

    @Override
    public void execute() {
        Common.callEvent(new DamageEnableEvent());
        Game.getGame().setDamageEnabled(true);
    }

    @Override
    public int getToggleTimer() {
        return getTimerSettings().getDamageTime();
    }

    @Override
    public String getCountdownBroadcast() {
        Extra.sound(Sounds.Countdown.Damage.TICK);
        return Messages.CountDown.DAMAGE_ANNOUNCE;
    }

    @Override
    public String getToggledBroadcast() {
        Extra.sound(Sounds.Countdown.Damage.RUN);
        return Messages.CountDown.DAMAGE_ENABLED;
    }
}
