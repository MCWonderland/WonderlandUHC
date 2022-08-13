package org.mcwonderland.uhc.hook.voice;

import github.scarsz.discordsrv.dependencies.jda.api.entities.VoiceChannel;
import org.mcwonderland.uhc.game.UHCTeam;

import java.util.HashMap;
import java.util.Map;

public class TeamVoices {
    private Map<UHCTeam, VoiceChannel> channels = new HashMap<>();

    public VoiceChannel getChannel(UHCTeam team) {
        return channels.get(team);
    }

    public void add(UHCTeam team, VoiceChannel channel) {
        channels.put(team, channel);
    }

    public VoiceChannel remove(UHCTeam team) {
        return channels.remove(team);
    }
}
