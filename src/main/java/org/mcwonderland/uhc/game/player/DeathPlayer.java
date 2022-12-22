package org.mcwonderland.uhc.game.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.model.InventoryContent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
@Setter
@RequiredArgsConstructor
public class DeathPlayer {
    private static HashMap<UHCPlayer, DeathPlayer> deathPlayers = new HashMap<>();

    private final InventoryContent invContent;
    private final Location location;
    private final float exp;
    private final int level;

    public static DeathPlayer getDeathPlayer(UHCPlayer uhcPlayer) {
        return deathPlayers.get(uhcPlayer);
    }

    public static DeathPlayer addDeathPlayer(UHCPlayer uhcPlayer) {
        return deathPlayers.put(uhcPlayer, makeDeathPlayer(uhcPlayer));
    }

    private static DeathPlayer makeDeathPlayer(UHCPlayer uhcPlayer) {
        if (uhcPlayer.isOnline()) {
            Player player = uhcPlayer.getPlayer();
            return new DeathPlayer(
                    new InventoryContent(player),
                    player.getLocation(),
                    player.getExp(),
                    player.getLevel());
        }

        CombatRelog combatRelog = CombatRelog.get(uhcPlayer);

        return new DeathPlayer(new InventoryContent(uhcPlayer.getPlayer()),
                combatRelog.getEntity().getLocation(),
                combatRelog.getXp(),
                combatRelog.getLevel());
    }

}
