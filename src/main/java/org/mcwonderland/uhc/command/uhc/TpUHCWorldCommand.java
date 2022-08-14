package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleSubCommand;

/**
 * 2019-11-24 下午 12:50
 */
public class TpUHCWorldCommand extends SimpleSubCommand {

    protected TpUHCWorldCommand(UHCMainCommandGroup parent, String subLabel) {
        super(parent, subLabel);

        setDescription("傳送至UHC世界。");
        setPermission(UHCPermission.COMMAND_UHC_TP.toString());
    }

    @Override
    protected void onCommand() {
        Player player = getPlayer();

        World world = UHCWorldUtils.getWorld();

        if (world != null) {
            player.teleport(UHCWorldUtils.getZeroZero());
            player.setGameMode(GameMode.CREATIVE);
        } else
            tell(Messages.Host.WORLD_DOESNT_EXIST);
    }
}