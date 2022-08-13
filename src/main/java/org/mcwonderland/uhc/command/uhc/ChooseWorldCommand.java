package org.mcwonderland.uhc.command.uhc;

import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.Remain;

/**
 * 2019-11-24 下午 12:50
 */
public class ChooseWorldCommand extends SimpleSubCommand {

    protected ChooseWorldCommand(UHCMainCommandGroup parent, String subLabel) {
        super(parent, subLabel);

        setMinArguments(0);
        setDescription("選擇此遊戲世界並開始載入地圖。");
    }

    @Override
    protected void onCommand() {
        if (CacheSaver.getLoadingStatus() == LoadingStatus.DONE)
            return;

        saveCaches();
        kickPlayers();
        Common.runLater(1, Extra::restartServer);
    }

    private void saveCaches() {
        CacheSaver.setLoadingStatus(LoadingStatus.GENERATING);

        if (isPlayer())
            CacheSaver.setHost(getPlayer().getName());

        CacheSaver.saveCache();
    }

    private void kickPlayers() {
        String msg = new SimpleReplacer(CommandSettings.Uhc.Choose.KICK_INIT_MSG).getMessages();

        for (Player player : Remain.getOnlinePlayers())
            PlayerUtil.kick(player, msg);
    }
}
