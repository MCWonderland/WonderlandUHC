package org.mcwonderland.uhc.command.impl.game;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.practice.Practice;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

public class PracticeCommand extends SimpleCommand {

    public PracticeCommand(String label) {
        super(label);

        setPermission(UHCPermission.COMMAND_PRACTICE.toString());
        setDescription("加入/退出練習模式");
    }

    @Override
    protected void onCommand() {
        Practice practice = WonderlandUHC.getInstance().getPractice();
        Player player = getPlayer();

        if (args.length >= 1) {
            checkPerm("wonderland.uhc.host.practiceother");
            player = findPlayer(args[0]);
        }

        if (practice.isInPractice(player))
            practice.quit(player);
        else
            practice.join(player);
    }
}
