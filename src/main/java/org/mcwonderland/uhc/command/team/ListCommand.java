package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;

class ListCommand extends TeamSubCommand {

    protected ListCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setUsage("[玩家]");
        setDescription("查看該玩家所處隊伍的資訊。");
        setPermission(UHCPermission.COMMAND_TEAM_LIST.toString());
    }

    @Override
    protected void onCommand() {
        if (args.length >= 1) {
            Player target = findPlayer(args[0]);
            sendTeamInfoAbout(target);
        } else
            sendTeamInfoAbout(getPlayer());
    }

    private void sendTeamInfoAbout(Player target) {
        UHCTeam targetTeam = UHCTeam.getTeam(target);
        checkNotNull(targetTeam, CommandSettings.Team.PLAYER_HAS_NO_TEAM);

        for (String msg : CommandSettings.TeamList.MESSAGES) {
            if (msg.contains("{playeralive}")) {
                for (UHCPlayer uhcPlayer : targetTeam.getAlives())
                    tell(msg.replace("{playeralive}", uhcPlayer.getName())
                            .replace("{heal}", "" + Extra.formatHealth(uhcPlayer.getEntity().getHealth())));
            } else if (msg.contains("{playerdeath}")) {
                for (UHCPlayer uhcPlayer : targetTeam.getPlayers())
                    if (uhcPlayer.isDead())
                        tell(msg.replace("{playerdeath}", uhcPlayer.getName()));
            } else
                tell(msg.replace("{color}", "" + targetTeam.getColor())
                        .replace("{character}", "" + targetTeam.getSymbol())
                        .replace("{teamname}", targetTeam.getName()));
        }

        Extra.sound(getPlayer(), Sounds.Commands.TEAM_INFO);
    }
}
