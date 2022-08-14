package org.mcwonderland.uhc.command.impl.host;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class StaffCommand extends SimpleCommand {

    public StaffCommand(String label) {
        super(label);

        setMinArguments(0);
        setDescription("切換管理員模式。");
        setPermission(UHCPermission.COMMAND_STAFF.toString());
    }

    @Override
    protected void onCommand() {
        checkConsole();

        Player player = getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        if (uhcPlayer.getRoleName() != RoleName.STAFF)
            uhcPlayer.changeStaffRole();
        else
            removePlayerStaff(uhcPlayer);
    }

    private void removePlayerStaff(UHCPlayer uhcPlayer) {
        if (!GameUtils.isWaiting())
            uhcPlayer.changeSpectatorRole();
        else
            uhcPlayer.changePlayerRole();
    }
}
