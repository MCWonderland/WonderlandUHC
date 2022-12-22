package org.mcwonderland.uhc.game.state.playing.listener.death;

import org.mcwonderland.uhc.events.UHCGamingDeathEvent;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.model.deathmsg.DeathMessageHandler;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.stats.UHCStats;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.LivingEntity;

class UHCDeathDataHandler {
    private DeathMessageHandler deathMessageHandler = DeathMessageHandler.getInstance();
    private final LivingEntity deathEntity;
    private final UHCPlayer deathPlayer;
    private final UHCPlayer killer;

    public UHCDeathDataHandler(UHCGamingDeathEvent event) {
        this.deathEntity = event.getEntity();
        this.deathPlayer = event.getUhcPlayer();
        this.killer = deathEntity.getKiller() == null ? null : UHCPlayer.getUHCPlayer(deathEntity.getKiller());
    }

    public void run() {
        handleStatsAndKillMessages();
        checkTeamEliminated();
    }

    private void handleStatsAndKillMessages() {
        if (killer != null && !killer.isDead() && deathPlayer != killer) {
            addKillStats();
            sendKilledMessage();
        }

        broadcastDeath();
    }

    private void broadcastDeath() {
        if (Settings.Misc.USE_PLUGIN_DEATH_MESSAGE) {
            String message = deathMessageHandler.getDeathMessage(deathPlayer);
            Chat.broadcast(message);
        }
    }

    private void addKillStats() {
        UHCStats stats = killer.getStats();
        stats.totalKills++;
        stats.kills++;
    }

    private void sendKilledMessage() {
        UHCTeam killerUTeam = killer.getTeam();

        Chat.send(deathPlayer.getPlayer(), Messages.Game.YOU_HAVE_BEEN_KILLED
                .replace("{killer}", killerUTeam.getChatFormat() + killer.getName())
                .replace("{heal}", "" + Extra.formatHealth(killer.getPlayer().getHealth()))
                .replace("{team}", killerUTeam.getName())
                .replace("{character}", killerUTeam.getSymbol()));
    }

    private void checkTeamEliminated() {
        UHCTeam deathPlayerUTeam = deathPlayer.getTeam();

        if (deathPlayerUTeam.isEliminate())
            Chat.broadcast(Messages.Game.TEAM_ELIMINATED
                    .replace("{team}", deathPlayerUTeam.getName()));
    }
}
