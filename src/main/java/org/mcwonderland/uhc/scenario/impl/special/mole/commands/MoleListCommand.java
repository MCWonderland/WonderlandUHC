package org.mcwonderland.uhc.scenario.impl.special.mole.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.impl.special.mole.ScenarioMole;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

@RequiredArgsConstructor
public class MoleListCommand implements MoleCommand{

    private final ScenarioMole mole;

    @Override
    public String getLabel() {
        return "list";
    }

    @Override
    public void onCommand(Player player, List<String> args) {
        UHCPlayer uhcPlayer = UHCPlayer.getUHCPlayer(player);

        CommandHelper.checkGameStarted();

        if (ScenarioMole.isMole(uhcPlayer) || uhcPlayer.getRoleName() == RoleName.STAFF)
            Chat.send(player, showMoleList());
        else
            Chat.send(player, ScenarioMole.getMoleCommandDeniedMessage());
    }

    private List<String> showMoleList() {

        List<String> list = new SimpleReplacer(CommandSettings.Mole.LIST).buildList();

        return replaceMessages(list);
    }

    @NotNull
    public static List<String> replaceMessages(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.contains("{molePlayers}")) {
                list.remove(s);
                for (String name : ScenarioMole.getMoleNames()) {
                    list.add(i, s.replace("{molePlayers}", name));
                }
            }
        }

        return list;
    }
}
