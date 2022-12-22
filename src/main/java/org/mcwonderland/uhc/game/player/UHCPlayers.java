package org.mcwonderland.uhc.game.player;

import lombok.experimental.UtilityClass;
import org.mcwonderland.uhc.api.enums.RoleName;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class UHCPlayers {
    public Set<UHCPlayer> getOnlines() {
        return toOnlines(UHCPlayer.getAllPlayers());
    }

    public Set<UHCPlayer> getStatusIs(RoleName... status) {
        return getBy(uhcPlayer -> Arrays.binarySearch(status, uhcPlayer.getRoleName()) >= 0);
    }

    public Stream<UHCPlayer> stream() {
        return UHCPlayer.getAllPlayers().stream();
    }

    public Stream<UHCPlayer> onlineStream() {
        return stream().filter(UHCPlayer::isOnline);
    }

    public int countOf(Predicate<? super UHCPlayer> predicate) {
        return Math.toIntExact(stream().filter(predicate).count());
    }

    public Set<UHCPlayer> getBy(Predicate<? super UHCPlayer> predicate) {
        return stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    public Set<UHCPlayer> getOnline(Predicate<? super UHCPlayer> predicate) {
        return onlineStream().filter(predicate).collect(Collectors.toSet());
    }

    public Set<String> toNames(Collection<UHCPlayer> uhcPlayers) {
        return uhcPlayers.stream().map(UHCPlayer::getName).collect(Collectors.toSet());
    }

    public Set<LivingEntity> toEntities(Collection<UHCPlayer> uhcPlayers) {
        Set<LivingEntity> list = new HashSet<>();

        for (UHCPlayer uhcPlayer : uhcPlayers) {
            LivingEntity entity = uhcPlayer.getEntity();

            if (entity != null)
                list.add(entity);
        }

        return list;
    }

    public Set<Player> toOnlinePlayers(Collection<UHCPlayer> uhcPlayers) {
        Set<Player> list = new HashSet<>();

        for (UHCPlayer uhcPlayer : uhcPlayers) {
            Player entity = uhcPlayer.getPlayer();

            if (entity.isOnline())
                list.add(entity);
        }

        return list;
    }

    public Set<UHCPlayer> toOnlines(Collection<UHCPlayer> uhcPlayers) {
        return uhcPlayers.stream().filter(UHCPlayer::isOnline).collect(Collectors.toSet());
    }

}
