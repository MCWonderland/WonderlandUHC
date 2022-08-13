package org.mcwonderland.uhc.tools.spectator;

import lombok.Getter;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.tools.UHCTool;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.mineacademy.fo.RandomUtil;

import java.util.Collection;
import java.util.stream.Collectors;

public class RandomTeleportItem extends UHCTool {

    @Getter
    private static final RandomTeleportItem instance = new RandomTeleportItem();

    private RandomTeleportItem() {
        super("Spectator.Random_Teleport");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Collection<Player> players = UHCPlayers.onlineStream()
                .filter(uhcPlayer -> uhcPlayer.getRoleName() == RoleName.PLAYER)
                .map(UHCPlayer::getPlayer).collect(Collectors.toSet());

        if (!players.isEmpty()) {
            Player target = RandomUtil.nextItem(players);
            GameUtils.spectateTeleport(player, target);
        }
    }
}
