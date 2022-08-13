package org.mcwonderland.uhc.scoreboard;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.mineacademy.fo.Common;

import java.util.List;

/**
 * @author crisdev333
 */
public class SimpleSidebar {
    @Getter
    private final Scoreboard scoreboard;
    @Getter
    private final Objective sidebar;

    private SimpleSidebar(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        this.sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 1; i <= 15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
    }

    public static SimpleSidebar createSidebarIn(Scoreboard scoreboard) {
        return new SimpleSidebar(scoreboard);
    }

    public final void setTitle(String title) {
        title = Common.colorize(title);
        sidebar.setDisplayName(StringUtils.left(title, 32));
    }

    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }
        text = Common.colorize(text);
        String pre = getFirstSplit(text);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if (scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    public void setSlotsFromList(List<String> list) {
        while (list.size() > 15) {
            list.remove(list.size() - 1);
        }

        int slot = list.size();

        if (slot < 15) {
            for (int i = (slot + 1); i <= 15; i++) {
                removeSlot(i);
            }
        }

        for (String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private final String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private final String getFirstSplit(String s) {
        return StringUtils.left(s, 16);
    }

    private final String getSecondSplit(String s) {
        if (s.length() > 32) {
            s = StringUtils.left(s, 32);
        }

        return s.length() > 16 ? s.substring(16) : "";
    }

}