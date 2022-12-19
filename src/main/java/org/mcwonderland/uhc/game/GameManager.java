package org.mcwonderland.uhc.game;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.event.timer.GameEndEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.model.freeze.FreezeMode;
import org.mcwonderland.uhc.scenario.impl.special.ScenarioMole;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameManager {

    public static void freeze(Player player) {
        FreezeMode freezeMode = Settings.Game.FREEZE_TYPE.getFreezeMode();
        freezeMode.freeze(player);
    }

    public static void unFreeze(Player player) {
        FreezeMode freezeMode = Settings.Game.FREEZE_TYPE.getFreezeMode();
        freezeMode.unFreeze(player);
    }

    public static Block getHighestBlock(World world, int x, int z) {
        for (int runY = world.getMaxHeight(); runY > 0; runY--) { // get highest block
            Block temp = world.getBlockAt(x, runY, z);
            if (!CompMaterial.isAir(temp) && !CompMaterial.isLongGrass(temp.getType()) && !CompMaterial.isDoublePlant(temp.getType())) {
                return temp;
            }
        }

        return world.getHighestBlockAt(x, z);
    }


    public static UHCTeam getWinner() {
        UHCTeam winningTeam = null;

        for (UHCTeam team : UHCTeam.getTeams()) {
            if (!team.isEliminate()) {
                if (winningTeam != null)
                    return null;
                else
                    winningTeam = team;
            }
        }

        return winningTeam;
    }

    public static void checkWin() {
        UHCTeam team = getWinner();

        if (team == null)
            return;

        broadcastWinning(team);
        Common.callEvent(new GameEndEvent());

        if (!WonderlandUHC.TEST_MODE)
            CacheSaver.deleteCache();
    }

    public static void checkMoleWin() {
        UHCTeam winnerTeam = getWinner();

        Set<UHCPlayer> molePlayers = ScenarioMole.getMoleList();

        HashSet<UHCPlayer> players = new HashSet<>();
        for (UHCTeam team : UHCTeam.getAliveTeams()) {
            players.addAll(team.getAlives());
        }
        if (molePlayers.containsAll(players)) {
            broadcastMoleWinning();
            Common.callEvent(new GameEndEvent());
        } else if (UHCTeam.getAliveTeams().size() == 1 && !players.contains(molePlayers)) {
            broadcastWinning(winnerTeam);
            Common.callEvent(new GameEndEvent());
        }
    }

    private static void broadcastMoleWinning() {
        List<String> winningMsg = getMoleWinningMsg();

        Chat.broadcast(winningMsg.toArray(new String[0]));
        Extra.sound(Sounds.Game.WIN);
    }

    private static List<String> getMoleWinningMsg() {
         int moleKills = 0;

        for (UHCPlayer player : ScenarioMole.getMoleList()) {
            moleKills += player.getKills();
        }

        SimpleReplacer simpleReplacer = new SimpleReplacer(Messages.Game.VICTORY_BROADCAST)
                .replace("{winner}", ScenarioMole.getMoleTeamName())
                .replace("{kills}", "" + moleKills)
                .replace("{host}", Game.getGame().getHost());

        List<String> list = simpleReplacer.buildList();

        //todo 優化code
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.contains("{players}")) {
                list.remove(s);
                for (String name : UHCPlayers.toNames(ScenarioMole.getMoleList())) {
                    list.add(i, s.replace("{players}", name));
                }
            }
        }

        return list;
    }

    private static void broadcastWinning(UHCTeam winner) {
        List<String> winningMsg = getWinningMsg(winner);

        Chat.broadcast(winningMsg.toArray(new String[0]));
        Extra.sound(Sounds.Game.WIN);
    }

    private static List<String> getWinningMsg(UHCTeam winner) {

        SimpleReplacer simpleReplacer = new SimpleReplacer(Messages.Game.VICTORY_BROADCAST)
                .replace("{winner}", winner.getName())
                .replace("{kills}", "" + winner.getKills())
                .replace("{host}", Game.getGame().getHost());

        List<String> list = simpleReplacer.buildList();

        //todo 優化code
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.contains("{players}")) {
                list.remove(s);
                for (String name : UHCPlayers.toNames(winner.getPlayers())) {
                    list.add(i, s.replace("{players}", name));
                }
            }
        }

        return list;
    }


    public static boolean isTeamFireDisabled(UHCPlayer p1, UHCPlayer p2) {
        if (p1.getTeam() == p2.getTeam() && !Game.getSettings().getTeamSettings().isAllowTeamFire())
            return true;

        return false;
    }
}
