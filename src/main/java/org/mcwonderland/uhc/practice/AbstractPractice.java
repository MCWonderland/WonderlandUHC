package org.mcwonderland.uhc.practice;

import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.GameUtils;
import org.mcwonderland.uhc.util.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.Remain;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractPractice implements Practice {
    private Collection<UUID> practicePlayers = new HashSet<>();

    @Override
    public final void setup() {
        getListeners().forEach(Common::registerEvents);
        onSetup();
    }

    protected abstract Collection<Listener> getListeners();

    protected abstract void onSetup();

    @Override
    public final void join(Player player) {
        if (!GameUtils.isWaiting()) {
            Chat.send(player, "&c現在無法加入練習模式。");
            return;
        }

        practicePlayers.add(player.getUniqueId());
        stuff(player);

        onJoin(player);
    }

    protected abstract void onJoin(Player player);

    @Override
    public final void quit(Player player) {
        if (!isInPractice(player)) {
            Chat.send(player, "&c你並未在練習模式中。");
            return;
        }

        practicePlayers.remove(player.getUniqueId());

        Extra.comepleteClear(player);
        Lobby.send(player);
    }

    @Override
    public Iterable<Player> getPlayers() {
        return practicePlayers.stream()
                .map(Remain::getPlayerByUUID)
                .filter(player -> player != null)
                .collect(Collectors.toSet());
    }

    @Override
    public final boolean isInPractice(Player player) {
        return practicePlayers.contains(player.getUniqueId());
    }
}
