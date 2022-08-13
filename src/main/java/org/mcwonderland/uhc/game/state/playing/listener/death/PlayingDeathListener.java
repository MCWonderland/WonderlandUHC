package org.mcwonderland.uhc.game.state.playing.listener.death;

import me.lulu.datounms.DaTouNMS;
import org.mcwonderland.uhc.UHCPermission;
import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameManager;
import org.mcwonderland.uhc.game.player.DeathPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.model.InventoryContent;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2019-12-07 下午 07:02
 */
public class PlayingDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        UHCPlayer uhcPlayer = UHCPlayer.getFromEntity(entity);

        if (uhcPlayer != null) {
            Common.callEvent(new UHCGamingDeathEvent(uhcPlayer, e));
        }
    }

    @EventHandler
    public void removeDeathMessages(PlayerDeathEvent e) {
        if (Settings.Misc.USE_PLUGIN_DEATH_MESSAGE)
            e.setDeathMessage(null);
    }

    @EventHandler
    public void handleDeathMessageAndData(UHCGamingDeathEvent e) {
        new UHCDeathDataHandler(e).run();
    }

    @EventHandler
    public void onGamingEntityDeath(UHCGamingDeathEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();
        Player player = uhcPlayer.getPlayer();

        uhcPlayer.changeSpectatorRole();
        checkDeathKick(player);
        if (Settings.Misc.DEATH_ANIMATION)
            DaTouNMS.getCommonNMS().playDeathAnimation(player);
    }

    private void checkDeathKick(Player player) {
        if (UHCPermission.BYPASS_KICK_DEATH.hasPerm(player))
            return;

        Integer seconds = Settings.Spectator.DEATH_KICK_SECONDS;
        Chat.send(player, Messages.Spectator.NO_PERM_TO_SPEC
                .replace("{time}", "" + seconds));

        Common.runLater(seconds * 20, () -> {
            PlayerUtil.kick(player, new SimpleReplacer(Messages.Spectator.DEATH_KICK_MESSAGE)
                    .replace("{player}", player.getName())
                    .getMessages());
        });
    }

    /*
    1. put deathplayer data // lowest
    2. swap inventory mode check // low
    3. put backpack and custom drops // normal
    4. other scenario modify drops or something.... //high
    5. timebomb // highest
    6. remove from player list and check win // monitor
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void handleDeathPlayerData(UHCGamingDeathEvent e) {
        setDropAsInventoryItem(e);
        DeathPlayer.addDeathPlayer(e.getUhcPlayer());
    }

    private void setDropAsInventoryItem(UHCGamingDeathEvent e) {
        List<ItemStack> drops = new ArrayList<>();

        UHCPlayer uhcPlayer = e.getUhcPlayer();
        CombatRelog relog = CombatRelog.get(uhcPlayer);

        if (relog != null)
            drops.addAll(Arrays.asList(relog.getInventoryContent().getAllItems()));
        else
            drops.addAll(Arrays.asList(InventoryContent.contentsOf(uhcPlayer.getPlayer())));

        e.getDrops().clear();
        e.getDrops().addAll(drops);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleDeathDrops(UHCGamingDeathEvent e) {
        e.getDrops().addAll(Game.getSettings().getItemSettings().getCustomDrops());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void removeGaming(UHCGamingDeathEvent e) {
        removeRelogCaches(e.getEntity());

        GameManager.checkWin();
    }

    private void removeRelogCaches(LivingEntity entity) {
        CombatRelog relog = CombatRelog.getByRelogEntity(entity);

        if (relog == null)
            return;

        relog.remove();
    }


}
