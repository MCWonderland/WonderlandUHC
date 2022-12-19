package org.mcwonderland.uhc.command.scenarioMole;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.ScenarioMole;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class MoleChatCommand extends SimpleSubCommand {
    protected MoleChatCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("[訊息]");
        setDescription("對間諜們發送訊息");
        setPermission(null);
    }

    @Override
    protected void onCommand() {
        UHCPlayer player = UHCPlayer.getUHCPlayer(getPlayer());

        CommandHelper.checkGameStarted();

        if (ScenarioMole.getMoleList().contains(player) && player.isAlive())
            sendMoleChat(player);
        else
            Chat.send(getSender(), ScenarioMole.getMoleCommandDeniedMessage());
    }

    private void sendMoleChat(UHCPlayer player) {
        String msg = Common.colorize(joinArgs(0, args.length));

        for (UHCPlayer uhcPlayer : UHCPlayer.getAllPlayers()) {
            if (ScenarioMole.getMoleList().contains(uhcPlayer))
                Chat.send(uhcPlayer.getPlayer(), Messages.ChatFormat.MOLE_CHAT
                        .replace("{player}", player.getName())
                        .replace("{msg}", msg));
        }
    }
}
