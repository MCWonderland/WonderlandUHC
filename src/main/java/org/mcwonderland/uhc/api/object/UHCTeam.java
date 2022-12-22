package org.mcwonderland.uhc.api.object;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.Set;

public interface UHCTeam {
    boolean containMember(UHCPlayer uhcPlayer);

    boolean isInvited(UHCPlayer uhcPlayer);

    void addInvited(UHCPlayer uhcPlayer);

    void join(UHCPlayer uhcPlayer);

    void leave(UHCPlayer uhcPlayer);

    void promote(UHCPlayer newOwner);

    void disband();

    void sendMessage(String... msg);

    Collection<LivingEntity> getAliveEntities();

    Collection<Player> getAlivePlayers();

    Collection<UHCPlayer> getAlives();

    int getPlayersAmount();

    int getKills();

    String getPrefix();

    boolean isFull();

    boolean isEmpty();

    boolean isEliminate();

    void setOpenJoin(boolean openJoin);

    void setColor(org.bukkit.ChatColor color);

    void setName(String name);

    void setSymbol(String symbol);

    void setChatFormat(String chatFormat);

    UHCPlayer getOwner();

    Set<UHCPlayer> getPlayers();

    boolean isOpenJoin();

    ChatColor getColor();

    String getName();

    String getSymbol();

    String getChatFormat();

    Inventory getBackpack();
}
