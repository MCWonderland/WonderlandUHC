package org.mcwonderland.uhc.game.player.role.staff;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.game.GameManager;
import org.mcwonderland.uhc.game.player.DeathPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.role.models.RoleApplier;
import org.mcwonderland.uhc.game.player.staff.StaffOptions;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.tools.Hotbars;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class RoleStaffApplier implements RoleApplier {

    @Override
    public void apply(Player player) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);
        StaffOptions option = uhcPlayer.getStaffOptions();

        Extra.comepleteClear(player);
        GameManager.unFreeze(player);
        player.setGameMode(GameMode.CREATIVE);
        DaTouNMS.getCommonNMS().setCanPickupExp(player, false);
        player.setWalkSpeed(option.getMCSpeed());
        player.setFlySpeed(option.getMCSpeed());
        Hotbars.giveSpecItems(player);
        Hotbars.giveStaffAddon(player);

        if (GameUtils.isGameStarted() && !uhcPlayer.isDead())
            DeathPlayer.addDeathPlayer(uhcPlayer);

        Chat.send(player, Messages.Staff.ENABLED);
        Extra.sound(player, Sounds.Commands.STAFF_ON);
    }

    @Override
    public void end(Player player) {
        Chat.send(player, Messages.Staff.DISABLED);
        Extra.sound(player, Sounds.Commands.STAFF_OFF);
    }
}
