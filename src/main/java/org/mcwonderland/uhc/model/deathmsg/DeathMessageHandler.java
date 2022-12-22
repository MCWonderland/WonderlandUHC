package org.mcwonderland.uhc.model.deathmsg;

import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.List;

public class DeathMessageHandler {

    private static DeathMessageHandler instance;

    private DeathMessageLoader messageLoader = new DeathMessageLoader();
    private DeathMessageFormatter formater = new DeathMessageFormatter();

    private UHCPlayer uhcPlayer;
    private EntityDamageEvent damageEvent;

    private DeathMessageHandler() {

    }

    public static DeathMessageHandler getInstance() {
        if (instance == null)
            instance = new DeathMessageHandler();
        return instance;
    }

    public String getDeathMessage(UHCPlayer uhcPlayer) {
        this.uhcPlayer = uhcPlayer;
        this.damageEvent = uhcPlayer.getPlayer().getLastDamageCause();

        List<String> messages = getMessages(damageEvent);
        String msg = pickOne(messages);

        return format(msg);
    }

    private String format(String msg) {
        SimpleReplacer replacer = new SimpleReplacer(msg);

        UHCTeam playerTeam = uhcPlayer.getTeam();

        replacer.replace("{player}", playerTeam.getChatFormat() + uhcPlayer.getName())
                .replace("{playerKills}", playerTeam.getKills() + "");

        if (damageEvent instanceof EntityDamageByEntityEvent)
            replacer.replace("{entity}", (( EntityDamageByEntityEvent ) damageEvent).getDamager().getName());

        Player killer = uhcPlayer.getPlayer().getKiller();
        if (killer != null) {
            UHCPlayer uhcKiller = UHCPlayer.getUHCPlayer(killer);
            UHCTeam killerTeam = uhcKiller.getTeam();
            replacer.replace("{killer}", killerTeam.getChatFormat() + uhcKiller.getName())
                    .replace("{killerKills}", killerTeam.getKills() + "");
        }

        return replacer.getMessages();
    }

    private String pickOne(List<String> messages) {
        if (messages.isEmpty())
            return "";

        return RandomUtil.nextItem(messages);
    }

    private List<String> getMessages(EntityDamageEvent damageEvent) {
        if (damageEvent == null)
            return messageLoader.getDefaultMessages();

        return getMessagesByCause(damageEvent.getCause());
    }

    private List<String> getMessagesByCause(EntityDamageEvent.DamageCause cause) {
        Player killer = uhcPlayer.getEntity().getKiller();
        if (killer != null && killer != uhcPlayer.getPlayer())
            return messageLoader.getPlayerKilledMessages();

        List<String> deathMessages = messageLoader.getDeathMessages(cause);

        if (deathMessages == null)
            deathMessages = messageLoader.getDefaultMessages();

        return deathMessages;
    }

}
