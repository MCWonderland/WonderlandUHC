package org.mcwonderland.uhc.game.timer.impl.countdown;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.UHCTeam;
import org.mcwonderland.uhc.game.timer.Countdown;
import org.mcwonderland.uhc.model.Teleporter;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.World;

import java.util.List;
import java.util.stream.Collectors;

public class NetherCloseCountdown extends Countdown {

    @Override
    public void execute() {
        World uhcWorld = UHCWorldUtils.getWorld();
        World netherWorld = UHCWorldUtils.getNether();

        List<UHCTeam> hasMemberInNetherTeams = UHCTeam.getAliveTeams().stream()
                .filter(team -> team.getAliveEntities().stream()
                        .anyMatch(livingEntity -> livingEntity.getWorld() == netherWorld))
                .collect(Collectors.toList());

        for (UHCTeam team : hasMemberInNetherTeams)
            Teleporter.randomTp(team, uhcWorld);

        Game.getSettings().setUsingNether(false);
    }

    @Override
    public int getToggleTimer() {
        return getTimerSettings().getDisableNetherTime();
    }

    @Override
    public String getCountdownBroadcast() {
        Extra.sound(Sounds.Countdown.NetherClose.TICK);
        return Messages.CountDown.NETHER_ANNOUNCE;
    }

    @Override
    public String getToggledBroadcast() {
        Extra.sound(Sounds.Countdown.NetherClose.RUN);
        return Messages.CountDown.NETHER_DISABLED;
    }
}
