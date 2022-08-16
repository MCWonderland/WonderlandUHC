package org.mcwonderland.uhc.scoreboard;

import com.google.common.collect.Sets;
import lombok.Getter;
import me.lulu.datounms.DaTouNMS;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.settings.Settings;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.remain.Remain;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@Getter
public final class SimpleScores {

    private static final HashMap<UUID, SimpleScores> allScores = new HashMap<>();

    private final Player player;
    private final Scoreboard scoreboard;
    private final SimpleSidebar sideBar;
    private final SidebarLinesGetter linesGetter = new SidebarLinesGetter();

    private SimpleScores(Player player) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.sideBar = SimpleSidebar.createSidebarIn(scoreboard);
    }

    public static void createScores(Player player) {
        SimpleScores scores = new SimpleScores(player);

        scores.getSideBar().setTitle(Game.getSettings().getTitle());
        player.setScoreboard(scores.getScoreboard());

        allScores.put(player.getUniqueId(), scores);

        if (CacheSaver.getLoadingStatus() == LoadingStatus.DONE)
            updateFirstPreventDataMissing(scores);
    }

    private static void updateFirstPreventDataMissing(SimpleScores scores) {
        scores.updateHeals();
        scores.updateNameTagColors();
        scores.updateSidebar();
    }

    public static Collection<SimpleScores> getAllScores() {
        return Sets.newHashSet(allScores.values());
    }

    public static void removeScores(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        allScores.remove(player.getUniqueId());
    }

    public static void clearNameTagColor(UHCPlayer uhcPlayer) {
        allScores.values().forEach(score -> {
            Team boardTeam = score.getOrRegisterTeam(uhcPlayer.getTeam());

            if (boardTeam.hasEntry(uhcPlayer.getName()))
                boardTeam.removeEntry(uhcPlayer.getName());

            if (boardTeam.getEntries().isEmpty())
                boardTeam.unregister();
        });
    }

    public static void unregisterTeam(UHCTeam team) {
        allScores.values().forEach(score -> score.getOrRegisterTeam(team).unregister());
    }


    /*
    在「每個人的Scoreboard」裡面新增「其他隊伍的顏色」

    例如有A、B、C三個隊伍
    這個系統會在 A 隊伍玩家的board裡面註冊 B 與 C 的Team，且在 B 隊裡面也會註冊 A 與 C 的Team...以此類推

    意思也就是說，Team這東西並不是「通用數值」，而是分別位於「每個玩家的數據」中的

    假如你想把A隊的名字設定為紅色，不是把A隊本身的顏色變紅，而是要把B與C隊中的「A隊顏色」變紅 (讓A在 B隊與C隊中 「看起來是紅的」)

    (Team架構)
    A {
        TeamB color,
        TeamC color
    }

    B {
        TeamA color
        TeamC color
    }

    C {
        TeamA color
        TeamB color
    }
     */
    public synchronized void updateNameTagColors() {
        if (!Settings.Team.TEAMS_ON_TAB)
            return;

        HashSet<UHCTeam> uhcTeams = getTeams();

        for (UHCTeam u : uhcTeams) {
            Team t = getOrRegisterTeam(u);

            String prefix = StringUtils.left(u.getPrefix(), 15);

            if (!t.getPrefix().equals(prefix)) {
                t.setPrefix(prefix);
                fixNameColors(t, u);
            }


            for (UHCPlayer o : u.getPlayers()) {
                String name = o.getName();
                if (!t.hasEntry(name)) {
                    t.addEntry(name);
                }
            }
        }
    }

    @NotNull
    private HashSet<UHCTeam> getTeams() {
        return Sets.newHashSet(UHCTeam.getTeams());
    }

    private synchronized Team getOrRegisterTeam(UHCTeam u) {
        String name = StringUtils.left(u.getName(), 16);
        Team team = scoreboard.getTeam(name);

        if (team == null)
            team = scoreboard.registerNewTeam(name);

        return team;
    }

    private synchronized void fixNameColors(Team t, UHCTeam u) {
        if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_13) && t.getColor() != u.getColor())
            t.setColor(u.getColor());
    }

    public synchronized void updateHeals() {
        updateNameHealth();
        updateTabHealth();
    }

    private void updateNameHealth() {
        Objective nameHeal = scoreboard.getObjective("namehealth");

        if (nameHeal == null && Settings.Misc.USE_BELOW_NAME_HEALTH) {
            nameHeal = scoreboard.registerNewObjective("namehealth", "health");
            nameHeal.setDisplaySlot(DisplaySlot.BELOW_NAME);
            nameHeal.setDisplayName(Game.getSettings().getScoreboardSettings().getHeartColor() + "§l❤");
        }
    }

    private void updateTabHealth() {
        Objective tabHeal = scoreboard.getObjective("tabhealth");
        TabHealthType healthType = Settings.Misc.TAB_HEALTH_TYPE;

        if (healthType != TabHealthType.NONE) {
            if (tabHeal == null) {
                tabHeal = scoreboard.registerNewObjective("tabhealth", healthType.name());
                tabHeal.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            }

            for (Player o : Remain.getOnlinePlayers())
                tabHeal.getScore(o.getName()).setScore(( int ) (o.getHealth() + DaTouNMS.getCommonNMS().getAbsorptionHeart(o)));
        }
    }


    public synchronized void updateSidebar() {
        SimpleSidebar sidebar = getSideBar();

        sidebar.setTitle(Game.getSettings().getTitle());
        sidebar.setSlotsFromList(linesGetter.getBoardLines(player));
    }
}
