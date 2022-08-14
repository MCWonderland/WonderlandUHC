package org.mcwonderland.uhc.command.impl.info;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.menu.impl.game.DisableItemListMenu;
import org.mineacademy.fo.command.SimpleCommand;

public class DisableItemsCommand extends SimpleCommand {

    public DisableItemsCommand(String label) {
        super(label);

        setPermission(UHCPermission.COMMAND_DISABLEITEMS.toString());
    }

    @Override
    protected void onCommand() {
        new DisableItemListMenu().displayTo(getPlayer());
    }

}
