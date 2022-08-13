package org.mcwonderland.uhc.util;

import org.mcwonderland.uhc.WonderlandUHC;
import org.bukkit.entity.Player;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.remain.Remain;

public class PlayerHider {

    public static void hide(Player player, Player target) {
        if (player.canSee(target)) {
            if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_13))
                player.hidePlayer(WonderlandUHC.getInstance(), target);
            else
                player.hidePlayer(target);
        }
    }

    public static void show(Player player, Player target) {
        if (!player.canSee(target))
            if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_13))
                player.showPlayer(WonderlandUHC.getInstance(), target);
            else
                player.showPlayer(target);
    }

    public static void hidePlayer(Player target) {
        Remain.getOnlinePlayers().forEach(p -> hide(p, target));
    }

    public static void showPlayer(Player target) {
        Remain.getOnlinePlayers().forEach(p -> show(p, target));
    }

    public static void hideAll(Player target) {
        Remain.getOnlinePlayers().forEach(p -> {
            hide(p, target);
            hide(target, p);
        });
    }

    public static void showAll(Player target) {
        Remain.getOnlinePlayers().forEach(p -> {
            show(p, target);
            show(target, p);
        });
    }



}
