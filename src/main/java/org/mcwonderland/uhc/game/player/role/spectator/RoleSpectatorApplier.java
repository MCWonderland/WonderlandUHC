package org.mcwonderland.uhc.game.player.role.spectator;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.game.player.role.models.RoleApplier;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.tools.Hotbars;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;

public class RoleSpectatorApplier implements RoleApplier {

    @Override
    public void apply(Player player) {
        Extra.comepleteClear(player);
        DaTouNMS.getCommonNMS().setCanPickupExp(player, false);
        player.spigot().setCollidesWithEntities(false);

        switch (Settings.Spectator.SPECTATE_MODE) {
            case SPECTATOR: //
                player.setGameMode(GameMode.SPECTATOR);
                break;
            case DEFAULT:
                player.setGameMode(GameMode.CREATIVE);
                Common.runLater(() -> Hotbars.giveSpecItems(player));
                break;
        }
    }

}
