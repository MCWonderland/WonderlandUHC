package org.mcwonderland.uhc.model.deathmsg;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

/**
 * 怎麼寫 load 跟 reload 比較順？
 */
public abstract class DeathMessages extends YamlConfig {
    private List<String> messages;

    protected DeathMessages(String stringCauses) {
        loadConfiguration(UHCFiles.MESSAGES);

        this.messages = getStringList(stringCauses);
    }

    public String getMessage(UHCPlayer uhcPlayer) {
        if (messages.isEmpty())
            return "";

        return replacePlaceholders(uhcPlayer, RandomUtil.nextItem(messages));
    }

    private String replacePlaceholders(UHCPlayer uhcPlayer, String s) {
        return s.replace("", "");
    }
}
