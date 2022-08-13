package org.mcwonderland.uhc.command.impl.info;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.Iterator;

/**
 * 2019-11-27 下午 01:05
 */
public class TopKillsCommand extends SimpleCommand {

    public TopKillsCommand(String label) {
        super(label);

        setMinArguments(0);
        setDescription("查看擊殺數排行榜。");
    }

    @Override
    protected void onCommand() {
        CommandHelper.checkGameStarted();
        Player player = getPlayer();

        Iterator<UHCPlayer> topKills = UHCPlayers.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getStats().kills, o1.getStats().kills))
                .iterator();
        int place = 0;

        for (String msg : CommandSettings.TopKills.MESSAGES) {
            if (msg.contains("{player}")) {
                if (topKills.hasNext()) {
                    UHCPlayer uhcPlayer = topKills.next();
                    tell(msg.replace("{player}", uhcPlayer.getName())
                            .replace("{number}", ++place + "")
                            .replace("{kills}", uhcPlayer.getStats().kills + ""));
                }
            } else
                tell(msg);
        }

        Extra.sound(player, Sounds.Commands.TOP_KILLS);
    }
}