package org.mcwonderland.uhc.model.broadcast;

import org.mcwonderland.uhc.Dependency;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.model.GamePlaceholderReplacer;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

/**
 * 2019-11-20 下午 07:50
 */
public abstract class AbstractBroadcastSender extends YamlConfig {

    private List<String> broadCastMessageModel;

    protected AbstractBroadcastSender(String type) {
        setPathPrefix(type);

        loadConfiguration(UHCFiles.BROADCAST);
        broadCastMessageModel = getStringList("Formatting");
    }

    public final void sendBroadcast(GameStartTimeInfo info) {
        List<String> list = getPlaceholderReplacedNewList(info);
        send(list);
    }

    private List<String> getPlaceholderReplacedNewList(GameStartTimeInfo info) {
        List<String> list = GamePlaceholderReplacer.replace(broadCastMessageModel);

        list = new SimpleReplacer(list)
                .replace("{host}", Game.getGame().getHost())
                .replace("{ip}", info.getIp())
                .replace("{join_time}", info.getJoinTime())
                .replace("{start_time}", info.getStartTime())
                .buildList();

        cleanColorCodes(list);

        return list;
    }

    private void cleanColorCodes(List<String> list) {
        for (int i = 0; i < list.size(); i++)
            list.set(i, Common.stripColors(list.get(i)));
    }

    protected abstract void send(List<String> messages);

    public abstract Dependency getDependency();

}
