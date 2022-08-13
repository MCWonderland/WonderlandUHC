package org.mcwonderland.uhc.scoreboard;

import lombok.Getter;
import org.mcwonderland.uhc.scoreboard.line.*;
import org.mcwonderland.uhc.scoreboard.line.*;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.*;

@Getter
public class SidebarTheme extends YamlConfig {

    private static final List<SidebarTheme> themes = new ArrayList<>();

    private final Set<UHCLines> lines = new HashSet<>();

    private UHCLines lobbyLines, startingLines;
    private UHCLines spectatorSoloLines, spectatorTeamsLines;
    private UHCLines staffSoloLines, staffTeamsLines;
    private UHCLines playerSoloLines, playerTeamsLines;

    public static SidebarTheme defaultTheme() {
        return themes.get(0);
    }

    public static SidebarTheme getThemeOrDefault(String name) {
        return themes.stream()
                .filter(sidebarTheme -> sidebarTheme.getName().equalsIgnoreCase(name))
                .findFirst().orElse(defaultTheme());
    }

    public static Collection<SidebarTheme> getAllThemes() {
        return themes;
    }

    public static void loadThemes() {
        new ThemeLoader().loadThemes();
    }

    public SidebarTheme(String sectionPrefix) {
        setPathPrefix(sectionPrefix);
        loadConfiguration(UHCFiles.SCOREBOARDS);
        loadScoreboardModels();
    }

    public String getName() {
        return getPathPrefix();
    }

    private void loadScoreboardModels() {
        lobbyLines = new LobbyLines(getStringList("Lobby"));
        startingLines = new StartingLines(getStringList("Starting"));
        spectatorSoloLines = new GameLines(getStringList("Spectator_Solo"));
        spectatorTeamsLines = new GameLines(getStringList("Spectator_Teams"));
        staffSoloLines = new StaffLines(getStringList("Staff_Solo"));
        staffTeamsLines = new StaffLines(getStringList("Staff_Teams"));
        playerSoloLines = new SoloLines(getStringList("Player_Solo"));
        playerTeamsLines = new TeamsLines(getStringList("Player_Teams"));

        lines.addAll(Arrays.asList(
                lobbyLines,
                startingLines,
                spectatorSoloLines,
                spectatorTeamsLines,
                staffSoloLines,
                staffTeamsLines,
                playerSoloLines,
                playerTeamsLines
        ));
    }

    private static class ThemeLoader extends YamlConfig {
        public ThemeLoader() {
            loadConfiguration(UHCFiles.SCOREBOARDS);

            loadThemes();
        }

        private void loadThemes() {
            themes.clear();

            Set<String> themeNames = getKeys(false);

            for (String key : themeNames) {
                themes.add(new SidebarTheme(key));
            }
        }
    }
}
