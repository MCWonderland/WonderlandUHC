package org.mcwonderland.uhc.game.border;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.BorderUtil;
import org.mcwonderland.uhc.util.UHCWorldUtils;

public class MovingBorder implements Border {

    @Override
    public void shrinkToNextSize() {
        BorderUtil.setBorders(UHCWorldUtils.getWorld(), Game.getGame().getCurrentBorder());
        BorderUtil.moverBorder18(BorderUtil.getShrinkSecondsCost());
    }

    @Override
    public void onCommand(int size) {
    }

    @Override
    public void onCountdown(int untilShrink) {

    }

    @Override
    public String getCountdownBroadcast() {
        return replace(Messages.CountDown.Border.Shrink.ANNOUNCE);
    }

    @Override
    public String getShrinkMessage() {
        return replace(Messages.CountDown.Border.Shrink.REDUCED);
    }

    private String replace(String msg) {
        return msg.replace("{size}",
                getBorderSettings().getFinalSizeOfShrinkModeBorder() + "");
    }
}
