package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.menu.MainGui;
import org.mineacademy.fo.command.SimpleSubCommand;

/**
 * 2019-11-24 下午 01:15
 */
public class EditCommand extends SimpleSubCommand {

    protected EditCommand(UHCMainCommandGroup parent, String subLabel) {
        super(parent, subLabel);

        setDescription("開啟設定介面。");
        setPermission(UHCPermission.COMMAND_UHC_EDIT.toString());
    }

    @Override
    protected void onCommand() {
        MainGui.abrirMenu(getPlayer());
    }
}
