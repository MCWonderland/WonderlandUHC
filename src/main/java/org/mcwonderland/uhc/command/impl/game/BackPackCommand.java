package org.mcwonderland.uhc.command.impl.game;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.Scenario;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class BackPackCommand extends SimpleCommand {

    public BackPackCommand(String label) {
        super(label);

        setPermission(null);
        setMinArguments(0);
        setUsage("[玩家]");
    }

    @Override
    protected void onCommand() {
        CommandHelper.checkGameStarted();

        if (!isBackPackEnabled())
            return;

        if (args.length == 1)
            openOthersBackPack();
        else
            openOwnBackPack();
    }

    private void openOthersBackPack() {
        checkPerm("wonderland.uhc.host.seebackpack");

        Player toOpen = findPlayer(args[0]);
        openOnesBackPack(toOpen);
    }

    private void openOwnBackPack() {
        Player player = getPlayer();

        CommandHelper.checkGamingPlayer(player);

        openOnesBackPack(player);
    }

    private boolean isBackPackEnabled() {
        Scenario bpMode = WonderlandUHC.getInstance().getScenarioManager().getScenario(ScenarioName.BACKPACK);
        return bpMode.isEnabled();
    }

    private void openOnesBackPack(Player toCheck) {
        getPlayer().openInventory(UHCTeam.getTeam(toCheck).getBackpack());
    }

}
