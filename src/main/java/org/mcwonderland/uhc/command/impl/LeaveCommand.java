package org.mcwonderland.uhc.command.impl;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class LeaveCommand extends SimpleCommand {

    public LeaveCommand(String label) {
        super(label);

        setMinArguments(0);
        setDescription("離開伺服器。");
        setPermission(UHCPermission.COMMAND_LEAVE.toString());
    }

    @Override
    protected void onCommand() {
        Extra.sendToFallbackServer(getPlayer());
    }
}