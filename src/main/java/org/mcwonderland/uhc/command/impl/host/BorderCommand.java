package org.mcwonderland.uhc.command.impl.host;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameTimerRunnable;
import org.mcwonderland.uhc.game.border.Border;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.settings.sub.UHCBorderSettings;
import org.mcwonderland.uhc.game.settings.sub.UHCTimerSettings;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class BorderCommand extends SimpleCommand {

    public BorderCommand(String label) {
        super(label);

        setMinArguments(1);
        setUsage("<大小>");
        setDescription("強制收縮邊界。");
    }

    @Override
    protected void onCommand() {
        CommandHelper.checkGameStarted();

        UHCGameSettings settings = Game.getSettings();
        UHCBorderSettings borderSettings = settings.getBorderSettings();
        Border border = borderSettings.getBorderType().getMode();
        UHCTimerSettings timerSettings = settings.getTimer();

        int size = findNumber(0, 1, borderSettings.getInitialBorder(), CommandSettings.Border.ONLY_NUMBER_BETWEEN);

        border.onCommand(size);
        timerSettings.setBorderShrinkTime(GameTimerRunnable.totalSecond + 11);
    }
}
