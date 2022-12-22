package org.mcwonderland.uhc.menu.impl;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;
import org.mineacademy.fo.menu.config.MenuSection;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PlayersMenu extends ConfigMenuPagged<Player> {

    public PlayersMenu(Iterable<Player> pages, MenuSection section) {
        super(null, section, pages);
    }

    public static PlayersMenu gamingPlayersMenu(World world, MenuSection section) {
        return new PlayersMenu(getWorldPlayers(world), section);
    }

    private static Collection<Player> getWorldPlayers(World world) {
        return UHCPlayers.onlineStream()
                .filter(uhcPlayer -> !uhcPlayer.isDead() && uhcPlayer.getPlayer().getWorld() == world)
                .sorted(Comparator.comparing(UHCPlayer::getName))
                .map(UHCPlayer::getPlayer)
                .collect(Collectors.toList());
    }

    @Override
    protected ItemStack convertToItemStack(Player target) {
        ItemCreator builder = ItemCreator
                .of(CompMaterial.PLAYER_HEAD)
                .name(target.getName());

        if (Bukkit.getOnlineMode())
            builder.skullOwner(target.getName());

        return builder.make();
    }

    @Override
    protected void onPageClick(Player player, Player target, ClickType click) {
        GameUtils.spectateTeleport(player, target);
    }
}
