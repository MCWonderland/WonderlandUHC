package org.mcwonderland.uhc.command.team;

import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;
import org.mineacademy.fo.model.SimpleComponent;
import org.mineacademy.fo.model.SimpleReplacer;

class InviteCommand extends TeamOwnerCommand {

    protected InviteCommand(TeamCommandGroup parent, String sublabel) {
        super(parent, sublabel);

        setMinArguments(1);
        setUsage("<玩家>");
        setDescription("邀請其他人加入你的隊伍。");
    }

    @Override
    protected void onOwnerCommand() {
        checkModeAndGameStatus();

        Player player = getPlayer();
        UHCTeam team = getTeam();
        UHCPlayer target = CommandHelper.findUHCPlayer(args[0]);

        checkInvitationValid(team, target);

        team.addInvited(target);
        team.sendMessage(new SimpleReplacer(CommandSettings.Team.Invite.INVITED)
                .replace("{player}", player.getName())
                .replace("{target}", target.getName())
                .toArray());

        sendInvitation(target);
    }

    private void checkInvitationValid(UHCTeam team, UHCPlayer target) {
        checkExecuteSelf(target);

        if (target.getTeam() == team)
            returnTell(CommandSettings.Team.Invite.ALREADY_IN_YOUR_TEAM);

        checkFull(team);
    }

    private void sendInvitation(UHCPlayer uhcPlayer) {
        Player target = uhcPlayer.getPlayer();

        for (String s : CommandSettings.Team.Invite.INVITATION_MESSAGES) {
            if (s.contains("{click-join}")) {
                String clickHere = CommandSettings.Team.Invite.CLICK_HERE;
                SimpleComponent.of(s.replace("{click-join}", clickHere))
                        .onClickRunCmd("/team join " + getPlayer().getName())
                        .onHover(clickHere)
                        .send(target);
            } else
                Chat.send(target, s.replace("{player}", getPlayer().getName()));
        }
    }
}
