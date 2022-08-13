package org.mcwonderland.uhc.model.broadcast.impl;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.mcwonderland.uhc.Dependency;
import org.mcwonderland.uhc.model.broadcast.AbstractBroadcastSender;
import org.mineacademy.fo.exception.FoException;

import java.util.List;

/**
 * 2019-11-20 下午 07:49
 */

public class DiscordBroadcastSender extends AbstractBroadcastSender {
    private final List<String> channelIds;
    private final String invalidChannel = getString("Invalid_Channel");

    public DiscordBroadcastSender() {
        super("Discord");

        this.channelIds = getStringList("Channel_Ids");
    }

    @Override
    protected void send(List<String> messages) {
        String msg = getFormatterMessage(messages);

        channelIds.forEach(channel -> {
            TextChannel textChannel = DiscordUtil.getTextChannelById(channel);

            if (textChannel == null)
                throw new FoException(invalidChannel);

            textChannel.sendMessage(msg).complete();
        });
    }

    private String getFormatterMessage(List<String> messages) {
        String result = String.join("\n", messages);
        result = DiscordUtil.convertMentionsFromNames(result, DiscordSRV.getPlugin().getMainGuild());

        return result;
    }

    @Override
    public Dependency getDependency() {
        return Dependency.DISCORD_SRV;
    }
}
