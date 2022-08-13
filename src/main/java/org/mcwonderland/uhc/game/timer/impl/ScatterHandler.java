package org.mcwonderland.uhc.game.timer.impl;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameManager;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.timer.Timer;
import org.mcwonderland.uhc.model.Teleporter;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.PlayerHider;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScatterHandler implements Timer {
    private int cacheTP = 0;
    private Iterator<UHCTeam> teleportingTeams;
    private Map<UHCTeam, Location> teleportedTeams = new HashMap<>();

    @Override
    public void run() {
        if (teleportingTeams.hasNext()) {
            UHCTeam team = teleportingTeams.next();
            scatter(team);
            broadcastScatting();
        }

        if (!teleportingTeams.hasNext()) {
            end();
        }
    }

    public void start() {
        teleportingTeams = UHCTeam.getTeams().iterator();
        cacheTP = UHCTeam.getTeams().size();
    }

    public void scatter(UHCTeam team) {
        Location location = Teleporter.randomTp(team, UHCWorldUtils.getWorld());

        for (Player player : team.getAlivePlayers()) {
            player.setSprinting(false);
            PlayerHider.showPlayer(player);
            GameManager.freeze(player);
        }

        teleportedTeams.put(team, location);
    }

    public void end() {
        Chat.broadcast(Messages.CountDown.SCATTER_FINISHED);
        Game.getGame().nextState();
    }

    private void broadcastScatting() {
        int all = UHCTeam.getAliveTeams().size();
        int teleported = teleportedTeams.size();

        Chat.broadcast(Messages.CountDown.SCATTING_PLAYERS
                .replace("{count}", "" + teleported)
                .replace("{total}", "" + all));
    }

    @Override
    public int runTick() {
        return Settings.Game.TELEPORT_PLAYERS_DELAY;
    }

    public Location getScatterLocation(UHCTeam team) {
        return teleportedTeams.get(team);
    }

    public int getTeleportedNumbers() {
        return teleportedTeams.size();
    }
}
