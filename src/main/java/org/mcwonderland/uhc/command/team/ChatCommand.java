package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;

class ChatCommand extends TeamSubCommand {

    protected ChatCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("[訊息]");
        setDescription("發對隊伍聊天訊息。");
    }

    @Override
    protected void onCommand() {
        UHCPlayer uhcPlayer = getUhcPlayer();
        UHCTeam team = getTeam();

        if (uhcPlayer.isDead()) {
            uhcPlayer.setTeamChat(false);
            returnTell(CommandSettings.Team.Chat.CANT_USE);
        }

        if (args.length == 0)
            toggleTeamChat(uhcPlayer);
        else
            sendTeamChat(team, uhcPlayer);
    }

    private void toggleTeamChat(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        uhcPlayer.setTeamChat(!uhcPlayer.isTeamChat());

        if (uhcPlayer.isTeamChat()) {
            tellTeamChatStatus(CommandSettings.Team.Chat.JOINED);
            Extra.sound(player, Sounds.Commands.TEAM_CHAT_ON);
        } else {
            tellTeamChatStatus(CommandSettings.Team.Chat.QUITTED);
            Extra.sound(player, Sounds.Commands.TEAM_CHAT_OFF);
        }
    }

    private void tellTeamChatStatus(String msg) {
        tell(msg.replace("{cmd}", getLabel()));
    }

    private void sendTeamChat(UHCTeam team, UHCPlayer uhcPlayer) {
        String msg = Common.colorize(joinArgs(0, args.length));

        team.sendMessage(Messages.ChatFormat.TEAM_CHAT
                .replace("{teamname}", team.getName())
                .replace("{color}", team.getColor() + "")
                .replace("{player}", uhcPlayer.getName())
                .replace("{msg}", msg));
    }
}
