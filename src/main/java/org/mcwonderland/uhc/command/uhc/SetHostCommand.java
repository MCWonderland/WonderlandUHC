package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleSubCommand;

public class SetHostCommand extends SimpleSubCommand {

    public SetHostCommand(UHCMainCommandGroup parent, String label) {
        super(parent, label);

        setDescription("設定本場遊戲主持人的名稱(並不會給予權限)");
        setPermission(UHCPermission.COMMAND_UHC_SETHOST.toString());
    }


    @Override
    protected void onCommand() {
        Player newHost = getPlayer();

        Game.getGame().setHost(newHost.getName());

        tell(CommandSettings.Uhc.SetHost.HOST_CHANGED
                .replace("{name}", newHost.getName()));
    }
}
