package org.mcwonderland.uhc.tools.lobby;

import lombok.Getter;
import org.mcwonderland.uhc.api.enums.TeamSplitMode;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.sub.UHCTeamSettings;
import org.mcwonderland.uhc.menu.MainGui;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.tools.UHCTool;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamsItem extends UHCTool {

    @Getter
    private static final TeamsItem instance = new TeamsItem();

    private TeamsItem() {
        super("Lobby.Teams");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UHCTeamSettings teamSettings = Game.getSettings().getTeamSettings();

        if (teamSettings.getTeamSplitMode() == TeamSplitMode.RANDOM) {
            Chat.send(player, Messages.Team.ONLY_IN_CHOSEN_MODE);
            return;
        }

        MainGui.abrirTeamList(player);
    }
}
