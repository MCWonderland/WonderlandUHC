package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.menu.impl.host.CenterCleanerMenu;
import org.mcwonderland.uhc.settings.Messages;
import org.mineacademy.fo.command.SimpleSubCommand;

/**
 * 2019-11-24 下午 12:50
 */
public class RegenWorldCommand extends SimpleSubCommand {

    protected RegenWorldCommand(UHCMainCommandGroup parent, String subLabel) {
        super(parent, subLabel);

        setDescription("重載遊戲世界.");
        setUsage("{seed}");
        setPermission(UHCPermission.COMMAND_UHC_REGEN.toString());
    }

    @Override
    protected void onCommand() {
        if (CacheSaver.getLoadingStatus() != LoadingStatus.DONE)
            new CenterCleanerMenu(null, getSeed()).displayTo(getPlayer());
        else
            tell(Messages.Host.NOT_GENERATING);
    }

    private String getSeed() {
        return args.length >= 1 ? args[0] : null;
    }
}
