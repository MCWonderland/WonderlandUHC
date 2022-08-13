package org.mcwonderland.uhc.listener;

import github.scarsz.discordsrv.dependencies.jda.api.entities.VoiceChannel;
import org.mcwonderland.uhc.api.event.team.TeamCreatedEvent;
import org.mcwonderland.uhc.api.event.team.TeamDisbandedEvent;
import org.mcwonderland.uhc.api.event.team.TeamJoinedEvent;
import org.mcwonderland.uhc.api.event.team.TeamQuitedEvent;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.hook.voice.DiscordVoiceHook;
import org.mcwonderland.uhc.hook.voice.TeamVoices;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.mineacademy.fo.Common;

import java.util.concurrent.TimeUnit;

public class VoiceListener implements Listener {

    private TeamVoices teamVoices = DiscordVoiceHook.getTeamVoices();

    @EventHandler
    public void onTeamCreated(TeamCreatedEvent e) {
        UHCTeam team = e.getTeam();

        Common.runAsync(() -> {
            VoiceChannel channel = DiscordVoiceHook.createHiddenChannel(team.getName());

            teamVoices.add(team, channel);
            team.getPlayers().forEach(uhcPlayer -> moveToTeamVoice(uhcPlayer, team));
        });
    }

    @EventHandler
    public void onTeamDisbanded(TeamDisbandedEvent e) {
        VoiceChannel voiceChannel = teamVoices.remove(e.getTeam());

        voiceChannel.getMembers().forEach(DiscordVoiceHook::moveToLobby);
        voiceChannel.delete().queueAfter(1000, TimeUnit.MILLISECONDS);
    }

    @EventHandler
    public void onJoinedTeam(TeamJoinedEvent e) {
        UHCPlayer uhcPlayer = e.getPlayer();
        moveToTeamVoice(uhcPlayer, e.getTeam());
    }

    @EventHandler
    public void onQuitedTeam(TeamQuitedEvent e) {
        DiscordVoiceHook.moveToLobby(e.getPlayer());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!e.getMessage().equalsIgnoreCase("/reconnect"))
            return;

        e.setCancelled(true);

        Player player = e.getPlayer();
        Chat.send(player, Messages.DiscordVoice.RECONNECTING);
        tryToConnect(UHCPlayer.getUHCPlayer(player));
    }

    private void tryToConnect(UHCPlayer uhcPlayer) {
        if (uhcPlayer.isDead())
            DiscordVoiceHook.moveToLobby(uhcPlayer);
        else if (uhcPlayer.getTeam() != null)
            moveToTeamVoice(uhcPlayer, uhcPlayer.getTeam());
        else
            DiscordVoiceHook.moveToLobby(uhcPlayer);
    }

    private void moveToTeamVoice(UHCPlayer uhcPlayer, UHCTeam team) {
        VoiceChannel channel = teamVoices.getChannel(team);

        if (channel != null)
            DiscordVoiceHook.moveVoiceChannel(uhcPlayer, channel);
    }
}
