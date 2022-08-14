package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.SimpleLocalization;

public class ReloadCommand extends SimpleSubCommand {

    public ReloadCommand(UHCMainCommandGroup parent, String label) {
        super(parent, label);

        setDescription("重載插件。");
        setPermission(UHCPermission.COMMAND_UHC_RELOAD.toString());
    }

    @Override
    protected void onCommand() {
        try {
            SimplePlugin.getInstance().reload();
            Chat.send(sender, SimpleLocalization.Commands.RELOAD_SUCCESS.replace("{plugin_name}", SimplePlugin.getNamed()).replace("{plugin_version}", SimplePlugin.getVersion()));
        } catch (final Throwable t) {
            Chat.send(sender, SimpleLocalization.Commands.RELOAD_FAIL.replace("{error}", t.getMessage() != null ? t.getMessage() : "unknown"));
            t.printStackTrace();
        }
    }
}
