package org.mcwonderland.uhc.menu.impl.game;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.config.ConfigMenuPagged;
import org.mineacademy.fo.menu.model.InventoryDrawer;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.ConfigItem;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.Remain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamSelectorMenu extends ConfigMenuPagged<UHCTeam> {

    private static Set<UUID> viewing = new HashSet<>();

    private final ConfigItem availableItem = section.getConfigItem("Available");
    private final ConfigItem fullItem = section.getConfigItem("Full");
    private final Button createOwnTeamButton;

    public static void updateMenu() {
        TeamSelectorMenu newMenu = new TeamSelectorMenu();

        viewing = viewing.stream().filter(uuid -> {
            Player player = Remain.getPlayerByUUID(uuid);
            if (player == null)
                return false;
            Menu menu = Menu.getMenu(player);
            if (menu != null && menu.getClass() == TeamSelectorMenu.class) {
                newMenu.displayTo(player);
                return true;
            }
            return false;
        }).collect(Collectors.toSet());
    }

    public TeamSelectorMenu() {
        super(null, UHCMenuSection.of("Team_Selector"), getOpenJoinTeams());

        createOwnTeamButton = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                player.closeInventory();
                player.performCommand("team create");
            }

            @Override
            public ItemStack getItem() {
                return section.getButtonItem("Create_Your_Own");
            }
        };
    }

    private static Iterable<UHCTeam> getOpenJoinTeams() {
        return UHCTeam.getTeams().stream().filter(UHCTeam::isOpenJoin).collect(Collectors.toList());
    }

    @Override
    protected ItemStack convertToItemStack(UHCTeam team) {
        ConfigItem item = getItem(team);


        return ItemCreator.of(
                item.getMaterial(),
                item.getName(),
                new SimpleReplacer(item.getLore())
                        .replace("{slots}", team.getPlayersAmount())
                        .replace("{max}", Game.getSettings().getTeamSettings().getTeamSize())
                        .replace("{name}", team.getName())
                        .replace("{color}", team.getColor())
                        .replace("{character}", team.getSymbol())
                        .replace("{players}", UHCPlayers.toNames(team.getPlayers()))
                        .buildList())
                .make();
    }

    private ConfigItem getItem(UHCTeam team) {
        if (team.isFull())
            return fullItem;

        return availableItem;
    }

    @Override
    protected void onPageClick(Player player, UHCTeam clickedTeam, ClickType click) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
        UHCTeam previousTeam = uhcPlayer.getTeam();

        if (clickedTeam == previousTeam || clickedTeam.isFull())
            return;

        clickedTeam.join(uhcPlayer);
    }

    @Override
    protected void onPostDisplay(InventoryDrawer drawer) {
        super.onPostDisplay(drawer);
        viewing.add(getViewer().getUniqueId());
    }

    @Override
    public ItemStack getItemAt(int slot) {

        if (slot == getSize() - 5)
            return createOwnTeamButton.getItem();

        return super.getItemAt(slot);
    }
}
