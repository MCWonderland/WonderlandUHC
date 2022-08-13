package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.border.BorderType;
import org.mcwonderland.uhc.game.settings.sub.UHCBorderSettings;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.menu.model.UHCNumberEditButton;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.BorderUtil;
import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigLeftOrRightButton;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.button.config.conversation.ConfigTimeEditButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.menu.config.ItemPath;
import org.mineacademy.fo.model.SimpleReplacer;

/**
 * 2019-12-15 下午 07:04
 */
public class BorderSettingsMenu extends ConfigMenu {

    private final ConfigMenuButton sizeButton, netherSizeButton;
    private final ConfigMenuButton finalBorderSizeButton;
    private final ConfigMenuButton borderTypeButton;
    private final ConfigMenuButton borderShrinkSpeed;
    private final ConfigMenuButton shrinkCaculator;

    public BorderSettingsMenu(Menu parent) {
        super(UHCMenuSection.of("Border"), parent);

        UHCBorderSettings borderSettings = Game.getSettings().getBorderSettings();

        sizeButton = new UHCNumberEditButton<Integer>(getButtonPath("Size")) {

            @Override
            public Number getOriginalNumber() {
                return borderSettings.getInitialBorder();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Number.BorderSize.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Number.BorderSize.SAVED;
            }

            @Override
            protected void handleNumber(Integer newValue) {
                borderSettings.setInitialBorder(newValue);
            }
        };

        netherSizeButton = new UHCNumberEditButton<Integer>(getButtonPath("Nether_Size")) {

            @Override
            public Number getOriginalNumber() {
                return borderSettings.getInitialNetherBorder();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Number.NetherBorderSize.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Number.NetherBorderSize.SAVED;
            }

            @Override
            protected void handleNumber(Integer newValue) {
                borderSettings.setInitialNetherBorder(newValue);
            }
        };

        finalBorderSizeButton = new UHCNumberEditButton<Integer>(getButtonPath("Final_Size_Of_Shrink_Mode_Border")) {

            @Override
            public Number getOriginalNumber() {
                return borderSettings.getFinalSizeOfShrinkModeBorder();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Number.FinalSizeOrShrinkModeBorder.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Number.FinalSizeOrShrinkModeBorder.SAVED;
            }

            @Override
            protected void handleNumber(Integer newValue) {
                borderSettings.setFinalSizeOfShrinkModeBorder(newValue);
            }
        };

        borderTypeButton = new BorderTypeButtonConfig(getButtonPath("Border_Type")) {
            @Override
            protected void setBorderType(Player player, BorderType type) {
                borderSettings.setBorderType(type);

                broadcast(Messages.Host.BORDER_TYPE_CHANGED, player);
            }

            @Override
            protected BorderType getBorderType() {
                return borderSettings.getBorderType();
            }
        };

        borderShrinkSpeed = new UHCNumberEditButton<Double>(getButtonPath("Border_Shrink_Speed")) {
            @Override
            public Double getOriginalNumber() {
                return borderSettings.getBorderShrinkSpeed();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Number.BorderShrinkSpeed.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Number.BorderShrinkSpeed.SAVED;
            }

            @Override
            protected void handleNumber(Double newValue) {
                borderSettings.setBorderShrinkSpeed(newValue);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replaceTime(BorderUtil.getShrinkSecondsCost());
            }
        };

        shrinkCaculator = new ConfigTimeEditButton(getButtonPath("Shrink_Calculator")) {
            private double speedResult;

            @Override
            protected void onAcceptInput(int time) {
                speedResult = getShrinkSpeed(time);

                borderSettings.setBorderShrinkSpeed(speedResult);
            }

            @Override
            protected int getCurrentTime() {
                return 0;
            }

            private double getShrinkSpeed(int time) {
                return BorderUtil.getShrinkSpeedPerSecond(time);
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Time.ShrinkCalculator.MESSAGE
                        .replace("{init}", borderSettings.getInitialBorder() + "")
                        .replace("{final}", borderSettings.getFinalSizeOfShrinkModeBorder() + "");
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Time.ShrinkCalculator.SAVED.replace("{speed}", speedResult + "");
            }
        };
    }

    private abstract class BorderTypeButtonConfig extends ConfigLeftOrRightButton {

        protected BorderTypeButtonConfig(ItemPath path) {
            super(path);

        }

        @Override
        protected final void onLeftClick(Player player, Menu menu) {
            setBorderType(player, BorderType.MOVING);
        }

        @Override
        protected final void onRightClick(Player player, Menu menu) {
            setBorderType(player, BorderType.TIMER);
        }

        @Override
        public void broadcast(String msg, Player clicker) {
            super.broadcast(msg.replace("{type}", getBorderType().fancyName()), clicker);
        }

        protected abstract void setBorderType(Player player, BorderType type);

        protected abstract BorderType getBorderType();

        @Override
        protected final SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
            return unReplacedLore.replace("{type}", getBorderType().fancyName());
        }
    }
}