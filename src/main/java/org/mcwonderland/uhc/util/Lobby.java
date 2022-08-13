package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.tools.Hotbars;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@UtilityClass
public class Lobby {

    public void send(Player player) {
        tpOnly(player);
        stuff(player);
    }

    public void stuff(Player player) {
        Hotbars.giveLobbyItems(player);

        player.setGameMode(GameMode.ADVENTURE);

        if (Settings.Misc.LOBBY_HIDE)
            PlayerHider.hideAll(player);
    }

    public void tpOnly(Player player) {
        player.teleport(UHCWorldUtils.getLobbySpawn());
    }

}
