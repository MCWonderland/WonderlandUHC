package org.mcwonderland.uhc.command.impl.info;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.model.GamePlaceholderReplacer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-12-02 下午 03:30
 */
public class ConfigCommand extends SimpleCommand {

    public ConfigCommand(String label) {
        super(label);

        setMinArguments(0);
        setDescription("查看遊戲相關設定。");
        setPermission(UHCPermission.COMMAND_CONFIG.toString());
    }

    @Override
    protected void onCommand() {
        tell(GamePlaceholderReplacer.replace(CommandSettings.Config.MESSAGES));
    }
}