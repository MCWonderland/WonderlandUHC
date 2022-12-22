package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.settings.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TeamModifier {
    private Iterator<UHCPlayer> noTeamPlayers;
    private Iterator<UHCTeam> teamIterator;

    public void splitTeams() {
        noTeamPlayers = getOnlineNoTeamPlayers();

        if (Settings.Team.ALONE_TEAMS)
            noTeamPlayers.forEachRemaining(UHCTeam::createTeamIfNotExist);
        else
            fillNonFullTeams();
    }

    private void fillNonFullTeams() {
        teamIterator = getTeams();

        UHCTeam currentTeam = null;

        while (noTeamPlayers.hasNext()) {

            if (currentTeam == null || containStaff(currentTeam) || currentTeam.isFull()) {
                if (teamIterator.hasNext())
                    currentTeam = teamIterator.next();
                else
                    currentTeam = UHCTeam.createTeamIfNotExist(noTeamPlayers.next());

                continue;
            }

            join(currentTeam, noTeamPlayers.next());
        }
    }

    private void join(UHCTeam team, UHCPlayer uhcPlayer) {
        if (uhcPlayer.getRoleName() == RoleName.STAFF)
            UHCTeam.createTeamIfNotExist(uhcPlayer);
        else
            team.join(uhcPlayer);
    }

    private boolean containStaff(UHCTeam currentTeam) {
        return currentTeam.getPlayers().stream()
                .anyMatch(uhcPlayer -> uhcPlayer.getRoleName() == RoleName.STAFF);
    }

    public void resetTeams() {
        UHCTeam.getTeams().forEach(UHCTeam::disband);
    }


    private Iterator<UHCTeam> getTeams() {
        ArrayList<UHCTeam> uhcTeams = new ArrayList<>(UHCTeam.getTeams());

        Collections.shuffle(uhcTeams);

        return uhcTeams.iterator();
    }

    private Iterator<UHCPlayer> getOnlineNoTeamPlayers() {
        List<UHCPlayer> list = UHCPlayers.stream()
                .filter(uhcPlayer -> uhcPlayer.isOnline() && uhcPlayer.getTeam() == null)
                .collect(Collectors.toList());

        Collections.shuffle(list);

        return list.iterator();
    }
}
