package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCTimerSettings;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.settings.Messages;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.button.config.conversation.ConfigTimeEditButton;
import org.mineacademy.fo.menu.config.ConfigMenu;

public class TimeSettingsMenu extends ConfigMenu {

    private final ConfigMenuButton borderShrink;
    private final ConfigMenuButton damage;
    private final ConfigMenuButton disableNether;
    private final ConfigMenuButton finalHeal;
    private final ConfigMenuButton pvp;

    public TimeSettingsMenu(Menu parent) {
        super(UHCMenuSection.of("Times"), parent);

        UHCTimerSettings timer = Game.getSettings().getTimer();

        pvp = new ConfigTimeEditButton(getButtonPath("Pvp")) {
            @Override
            protected void onAcceptInput(int time) {
                timer.setPvpTime(time);
            }

            @Override
            protected int getCurrentTime() {
                return timer.getPvpTime();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Time.Pvp.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Time.Pvp.SAVED;
            }

        };

        damage = new ConfigTimeEditButton(getButtonPath("Damage")) {

            @Override
            protected void onAcceptInput(int time) {
                timer.setDamageTime(time);
            }

            @Override
            protected int getCurrentTime() {
                return timer.getDamageTime();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Time.Damage.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Time.Damage.SAVED;
            }
        };

        finalHeal = new ConfigTimeEditButton(getButtonPath("Final_Heal")) {
            @Override
            protected void onAcceptInput(int time) {
                timer.setHealTime(time);
            }

            @Override
            protected int getCurrentTime() {
                return timer.getHealTime();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Time.FinalHeal.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Time.FinalHeal.SAVED;
            }

        };

        borderShrink = new ConfigTimeEditButton(getButtonPath("Border_Shrink")) {
            @Override
            protected void onAcceptInput(int time) {
                timer.setBorderShrinkTime(time);
            }

            @Override
            protected int getCurrentTime() {
                return timer.getBorderShrinkTime();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Time.BorderShrink.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Time.BorderShrink.SAVED;
            }
        };


        disableNether = new ConfigTimeEditButton(getButtonPath("Disable_Nether")) {
            @Override
            protected void onAcceptInput(int time) {
                timer.setDisableNetherTime(time);
            }

            @Override
            protected int getCurrentTime() {
                return timer.getDisableNetherTime();
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Time.DisableNether.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Time.DisableNether.SAVED;
            }
        };
    }
}
