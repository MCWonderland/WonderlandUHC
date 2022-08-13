package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.scoreboard.line.ScoreLines;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.ConfigItem;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class SidebarThemeSettingsMenu extends ConfigMenuPagged<SidebarTheme> {

    private final Player player;
    private final UHCPlayer uhcPlayer;

    public SidebarThemeSettingsMenu(Menu parent, Player player) {
        super(parent, UHCMenuSection.of("Sidebar_Theme_Selector"), SidebarTheme.getAllThemes());

        this.player = player;
        this.uhcPlayer = UHCPlayer.getUHCPlayer(player);
    }

    @Override
    protected ItemStack convertToItemStack(SidebarTheme theme) {
        ConfigItem info = section.getConfigItem("Themes");

        return ItemCreator.of(
                info.getMaterial(),
                info.getName().replace("{theme_name}", theme.getName()),
                getPreview(theme, info.getLore())).make();
    }

    private List<String> getPreview(SidebarTheme theme, List<String> lore) {

        return new SimpleReplacer(lore)
                .replace("{theme_preview}", getTestLinesIn(theme).getFor(uhcPlayer))
                .buildList();
    }

    private ScoreLines getTestLinesIn(SidebarTheme theme) {
        ScoreLines lobbyLines = theme.getLobbyLines();

        updateVarPreventNullPointer(lobbyLines);

        return lobbyLines;
    }

    private void updateVarPreventNullPointer(ScoreLines lobbyLines) {
        lobbyLines.updateGlobalVariables();
    }

    @Override
    protected void onPageClick(Player player, SidebarTheme sidebarTheme, ClickType clickType) {
        Game.getSettings().getScoreboardSettings().setSidebarTheme(sidebarTheme);
        getParent().displayTo(player);
    }

    @Override
    protected String[] getInfo() {
        return null;
    }
}
