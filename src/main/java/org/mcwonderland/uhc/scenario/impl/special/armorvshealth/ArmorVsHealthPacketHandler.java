package org.mcwonderland.uhc.scenario.impl.special.armorvshealth;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ArmorVsHealthPacketHandler {

    private List<UpdateCallback> callbacks = new ArrayList<>();

    abstract void register();

    abstract void unRegister();

    void trigger(Player player) {
        this.callbacks.forEach(cb -> cb.callback(player));
    }

    final void listen(UpdateCallback callback) {
        this.callbacks.add(callback);
    }
}

@FunctionalInterface
interface UpdateCallback {
    void callback(Player player);
}