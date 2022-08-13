package org.mcwonderland.uhc.command.impl.info;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.menu.impl.game.EnabledScenariosMenu;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 上午 10:32
 */
public class ScenariosCommand extends SimpleCommand {

    private WonderlandUHC plugin = WonderlandUHC.getInstance();

    public ScenariosCommand(String label) {
        super(label);

        setMinArguments(0);
        setDescription("查看本場使用的特殊模式。");
    }

    @Override
    protected void onCommand() {
        new EnabledScenariosMenu(plugin.getScenarioManager()).displayTo(getPlayer());
    }
}
