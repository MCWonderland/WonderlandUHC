package org.mcwonderland.uhc.command.scenarioMole;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.scenario.impl.special.ScenarioMole;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class MoleListCommand extends SimpleSubCommand {

    public MoleListCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("");
        setMinArguments(0);
        setDescription("查看間諜名單");
    }

    @Override
    protected void onCommand() {
        UHCPlayer player = UHCPlayer.getUHCPlayer(getPlayer());

        CommandHelper.checkGameStarted();

        if (ScenarioMole.getMoleList().contains(player))
            for (UHCPlayer uhcPlayer : UHCPlayer.getAllPlayers()) {
                if (ScenarioMole.getMoleList().contains(uhcPlayer) && uhcPlayer.isAlive())
                    Chat.send(uhcPlayer.getPlayer(), showMoleList());
            }
        else
            Chat.send(getSender(), ScenarioMole.getMoleCommandDeniedMessage());
    }

    private List<String> showMoleList() {
        SimpleReplacer simpleReplacer = new SimpleReplacer(CommandSettings.Mole.List);

        List<String> list = simpleReplacer.buildList();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.contains("{molePlayers}")) {
                list.remove(s);
                for (String name : UHCPlayers.toNames(ScenarioMole.getMoleList())) {
                    list.add(i, s.replace("{molePlayers}", name));
                }
            }
        }

        return list;
    }
}
