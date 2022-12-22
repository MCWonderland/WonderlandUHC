package org.mcwonderland.uhc.game.state.playing.listener;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.player.staff.OreAlert;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.stats.UHCStats;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Collection;

public class StatsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(UHCBlockBreakEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();
        UHCStats stats = uhcPlayer.getStats();

        int mined = stats.addOreMined(e.getBlockType());

        if (mined % 5 == 0)
            StaffAlert.handleAlerts(e, mined);
    }

    @UtilityClass
    private static class StaffAlert {
        private UHCBlockBreakEvent e;
        private UHCPlayer miner;
        private int amount;

        private void handleAlerts(UHCBlockBreakEvent event, int mined) {
            e = event;
            miner = event.getUhcPlayer();
            amount = mined;

            staffAlert();
        }

        private void staffAlert() {
            OreAlert alert = OreAlert.fromMaterial(e.getBlockType());

            if (alert == null)
                return;

            Collection<UHCPlayer> staffs = getStaffsToggleAlertFor(alert);

            staffs.forEach(uhcPlayer -> Chat.send(uhcPlayer.getPlayer(), Messages.Staff.MINED_ALERT
                    .replace("{player}", miner.getName())
                    .replace("{block}", alert.colorizedName())
                    .replace("{amount}", amount + "")));
        }

        private Collection<UHCPlayer> getStaffsToggleAlertFor(OreAlert alert) {
            return UHCPlayers.getBy(uhcPlayer -> uhcPlayer.getRoleName() == RoleName.STAFF
                    && uhcPlayer.isOnline()
                    && uhcPlayer.getStaffOptions().hasOreAlert(alert)
            );
        }
    }

}
