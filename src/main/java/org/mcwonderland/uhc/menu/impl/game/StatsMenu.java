package org.mcwonderland.uhc.menu.impl.game;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.stats.UHCStats;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigClickableButton;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.menu.config.ItemPath;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;

/**
 * 2019-12-02 下午 11:12
 */
public class StatsMenu extends ConfigMenu {

    private final ConfigMenuButton playedButton;
    private final ConfigMenuButton winsButton;
    private final ConfigMenuButton killsButton;
    private final ConfigMenuButton kdrButton;

    public StatsMenu(Player player) {
        super(UHCMenuSection.of("Stats"), null);

        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
        UHCStats stats = uhcPlayer.getStats();

        int played = stats.gamePlayed;
        int wins = stats.totalWins;
        int kills = stats.totalKills;
        double kdr = stats.getKdr();

        playedButton = new StatsButton(getButtonPath("Played"), "{played}", played + "");
        winsButton = new StatsButton(getButtonPath("Wins"), "{wins}", wins + "");
        killsButton = new StatsButton(getButtonPath("Kills"), "{kills}", kills + "");
        kdrButton = new StatsButton(getButtonPath("Kdr"), "{kdr}", kdr + "");
    }

    private class StatsButton extends ConfigClickableButton {

        private final String statsPlaceholder;
        private final String toReplaceValue;

        public StatsButton(ItemPath path, String statsPlaceholder, String toReplaceValue) {
            super(path);

            this.statsPlaceholder = statsPlaceholder;
            this.toReplaceValue = toReplaceValue;
        }

        @Override
        protected final void onClicked(Player player, Menu menu, ClickType click) {

        }

        @Override
        protected final SimpleSound getClickSound() {
            return null;
        }

        @Override
        protected final String replaceName(String unReplacedName) {
            return super.replaceName(unReplacedName)
                    .replace(statsPlaceholder, toReplaceValue);
        }

        @Override
        protected final SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
            return super.replaceLore(unReplacedLore)
                    .replace(statsPlaceholder, toReplaceValue);
        }
    }
}