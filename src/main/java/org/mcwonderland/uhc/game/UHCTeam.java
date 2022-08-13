package org.mcwonderland.uhc.game;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mcwonderland.uhc.api.event.team.TeamCreatedEvent;
import org.mcwonderland.uhc.api.event.team.TeamDisbandedEvent;
import org.mcwonderland.uhc.api.event.team.TeamJoinedEvent;
import org.mcwonderland.uhc.api.event.team.TeamQuitedEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.menu.impl.game.TeamSelectorMenu;
import org.mcwonderland.uhc.scoreboard.SimpleScores;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.tools.Hotbars;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.remain.CompColor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UHCTeam {
    private static final Set<UHCTeam> teams = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    private UHCPlayer owner;
    private Set<UHCPlayer> players = new HashSet<>();
    @Getter(AccessLevel.PRIVATE)
    private Set<UHCPlayer> invites = new HashSet<>();
    private boolean openJoin;
    private ChatColor color;
    private String name;
    private String symbol = "";
    private String chatFormat = "";
    //todo 這東西不該寫在這
    private Inventory backpack;

    public static Set<UHCTeam> getTeams() {
        return Sets.newHashSet(teams);
    }

    public static UHCTeam createTeamIfNotExist(UHCPlayer uhcPlayer) {
        UHCTeam team = uhcPlayer.getTeam();

        if (team == null) {
            team = new UHCTeam(uhcPlayer);
            teams.add(team);
            updateLobbyItems(uhcPlayer);
            Common.callEvent(new TeamCreatedEvent(team, uhcPlayer));
        }

        return team;
    }

    public static UHCTeam getTeam(Player player) {
        return UHCPlayer.getUHCPlayer(player).getTeam();
    }

    public static Set<UHCTeam> getAliveTeams() {
        return teams.stream()
                .filter(team -> !team.isEliminate())
                .collect(Collectors.toSet());
    }

    private static void updateLobbyItems(UHCPlayer uhcPlayer) {
        if (uhcPlayer.isOnline() && GameUtils.isWaiting()) {
            Player player = uhcPlayer.getPlayer();
            player.getInventory().clear();
            Hotbars.giveLobbyItems(player);
        }
    }

    private UHCTeam(UHCPlayer creator) {
        owner = creator;
        name = Settings.Team.DEFAULT_NAME.replace("{player}", creator.getName());
        symbol = generateNonRepeatedSymbol();
        color = Settings.Team.GIVE_DEFAULT_TEAM_RANDOM_COLOR ? getRandomColor() : ChatColor.WHITE;
        createBackpack();

        join(creator);
    }

    private String generateNonRepeatedSymbol() {
        Set<String> symbols = teams.stream().map(UHCTeam::getSymbol).collect(Collectors.toSet());

        String symbol = teams.size() + "";

        int run = 0;
        while (symbols.contains(symbol)) {
            symbol = run + "";
            run++;
        }

        return symbol;
    }

    private ChatColor getRandomColor() {
        List<ChatColor> chatColors = CompColor.getChatColors();
        int colorIndex = teams.size() % chatColors.size();

        return chatColors.get(colorIndex);
    }

    private void createBackpack() {
        Integer size = 27;//(( ScenarioBackPack ) Scenarios.BACKPACK.getData()).getSize();
        String name = Common.colorize(this.name);
        backpack = Bukkit.createInventory(null, size, name);
    }

    public boolean containMember(UHCPlayer uhcPlayer) {
        return uhcPlayer.getTeam() == this;
    }

    public boolean isInvited(UHCPlayer uhcPlayer) {
        return invites.contains(uhcPlayer);
    }

    public void addInvited(UHCPlayer uhcPlayer) {
        invites.add(uhcPlayer);
    }

    public void join(UHCPlayer uhcPlayer) {
        if (containMember(uhcPlayer))
            return;

        invites.remove(uhcPlayer);
        quitPreviousTeam(uhcPlayer);
        players.add(uhcPlayer);
        uhcPlayer.setTeam(this);
        TeamSelectorMenu.updateMenu();

        sendMessage(Messages.Team.PLAYER_JOINED
                .replace("{player}", uhcPlayer.getName())
                .replace("{players}", players.size() + "")
                .replace("{max}", GameUtils.getTeamSize() + ""));

        Common.callEvent(new TeamJoinedEvent(this, uhcPlayer));
    }

    private void quitPreviousTeam(UHCPlayer uhcPlayer) {
        UHCTeam team = uhcPlayer.getTeam();

        if (team != null)
            team.leave(uhcPlayer);
    }

    public void leave(UHCPlayer uhcPlayer) {
        SimpleScores.clearNameTagColor(uhcPlayer);

        remove(uhcPlayer);
        updateLobbyItems(uhcPlayer);
        TeamSelectorMenu.updateMenu();

        sendMessage(Messages.Team.PLAYER_LEFT
                .replace("{player}", uhcPlayer.getName())
                .replace("{players}", (players.size()) + "")
                .replace("{max}", GameUtils.getTeamSize() + ""));

        if (owner == uhcPlayer)
            tryToGiveOwnerToOthers();

        Common.callEvent(new TeamQuitedEvent(this, uhcPlayer));
    }

    private void remove(UHCPlayer uhcPlayer) {
        if (!containMember(uhcPlayer))
            return;

        players.remove(uhcPlayer);
        uhcPlayer.setTeam(null);
    }

    private void tryToGiveOwnerToOthers() {
        if (!isEmpty())
            promote(RandomUtil.nextItem(players));
        else
            disband();
    }

    public void promote(UHCPlayer newOwner) {
        UHCPlayer previousOwner = this.owner;
        setOwner(newOwner);

        updateLobbyItems(previousOwner);
        updateLobbyItems(newOwner);

        sendMessage(Messages.Team.PLAYER_PROMOTED.replace("{player}", newOwner.getName()));
    }

    public void disband() {
        new HashSet<>(players).forEach(uhcPlayer -> {
            uhcPlayer.setTeam(null);
            updateLobbyItems(uhcPlayer);
        });

        players.clear();
        teams.remove(this);

        TeamSelectorMenu.updateMenu();
        SimpleScores.unregisterTeam(this);

        Common.callEvent(new TeamDisbandedEvent(this));
    }

    public void sendMessage(String... msg) {
        UHCPlayers.toEntities(players).forEach(player -> Chat.send(player, msg));
    }

    public Collection<LivingEntity> getAliveEntities() {
        return UHCPlayers.toEntities(getAlives());
    }

    public Collection<Player> getAlivePlayers() {
        return UHCPlayers.toOnlinePlayers(getAlives());
    }

    public Collection<UHCPlayer> getAlives() {
        return players.stream().filter(uhcPlayer -> !uhcPlayer.isDead()).collect(Collectors.toList());
    }

    public int getPlayersAmount() {
        return players.size();
    }

    public int getKills() {
        int total = 0;

        for (UHCPlayer player : players)
            total += player.getStats().kills;

        return total;
    }

    public String getPrefix() {
        StringBuilder prefix = new StringBuilder();

        prefix.append(color);

        if (Settings.Team.CHARACTER_BOLD_DEFAULT)
            prefix.append(ChatColor.BOLD);

        prefix.append(symbol + color + " ");

        return prefix.toString();
    }

    public boolean isFull() {
        return getPlayersAmount() >= Game.getSettings().getTeamSettings().getTeamSize();
    }

    public boolean isEmpty() {
        return getPlayersAmount() <= 0;
    }

    public boolean isEliminate() {
        return !players.stream().anyMatch(uhcPlayer -> !uhcPlayer.isDead());
    }
}
