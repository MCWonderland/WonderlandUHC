package org.mcwonderland.uhc.scenario.impl.special.mole.commands;

import org.bukkit.entity.Player;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.mole.ScenarioMole;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;

import java.util.List;

public class MoleChatCommand implements MoleCommand {

    private ScenarioMole mole;

    public MoleChatCommand(ScenarioMole mole) {
        this.mole = mole;
    }

    @Override
    public String getLabel() {
        return "chat";
    }

    @Override
    public void onCommand(Player player, List<String> args) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        CommandHelper.checkGameStarted();

        if (ScenarioMole.isMole(uhcPlayer) && uhcPlayer.isAlive())
            sendMoleChat(uhcPlayer, String.join(" ", args.subList(1, args.size())));
        else
            Chat.send(player, ScenarioMole.getMoleCommandDeniedMessage());
    }

    private void sendMoleChat(UHCPlayer player, String msg) {

        for (UHCPlayer uhcPlayer : UHCPlayer.getAllPlayers()) {
            if (ScenarioMole.isMole(uhcPlayer))
                Chat.send(uhcPlayer.getPlayer(), Messages.ChatFormat.MOLE_CHAT
                        .replace("{player}", player.getName())
                        .replace("{msg}", msg));
        }
    }
}
