package org.mcwonderland.uhc.game.state.playing.listener;

import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.GameUtils;
import org.mcwonderland.uhc.util.UHCWorldUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.ReflectionUtil;

import java.lang.reflect.Method;

public class PortalListener implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        Game game = Game.getGame();

        if (!GameUtils.isGamingPlayer(p)) {
            e.setCancelled(true);
            return;
        }

        if (getToLocation(e).getWorld() == UHCWorldUtils.getNether()) {
            if (!game.getSettings().isUsingNether()) {
                cancelJoin(e, p, Messages.Game.NO_NETHER);
                return;
            }

            if (!game.isPvpEnabled() && !Settings.Game.NETHER_BEFORE_PVP) {
                cancelJoin(e, p, Messages.Game.CANT_JOIN_BEFORE_PVP_ENABLED);
                return;
            }
        }

        Location to = getSameCoordsLocation(e);
        to.getChunk().load();
        e.setTo(to);

        if (MinecraftVersion.olderThan(MinecraftVersion.V.v1_14)) {
            openPortalAgent(e);
            findOrCreate(e);
        }
    }

    private void cancelJoin(Cancellable cancellable, Player player, String msg) {
        cancellable.setCancelled(true);

        Chat.send(player, msg);
        Extra.sound(player, Sounds.Game.CANT_JOIN_NETHER);
    }

    private void openPortalAgent(PlayerPortalEvent e) {
        Method method = ReflectionUtil.getMethod(e.getClass(), "useTravelAgent", boolean.class);

        ReflectionUtil.invoke(method, e, true);
    }

    private void findOrCreate(PlayerPortalEvent e) {
        Object portalAgent = ReflectionUtil.invoke("getPortalTravelAgent", e);

        ReflectionUtil.invoke("findOrCreate", portalAgent, e.getTo());
    }

    private Location getSameCoordsLocation(PlayerPortalEvent e) {
        Location to = getToLocation(e);

        double borderMultiple = ( double ) Game.getGame().getCurrentBorder() / ( double ) GameUtils.getCurrentNetherBorder();

        World.Environment environment = to.getWorld().getEnvironment();

        if (environment == World.Environment.NETHER) {
            to.setX(to.getX() / borderMultiple);
            to.setZ(to.getZ() / borderMultiple);
        } else if (environment == World.Environment.NORMAL) {
            to.setX(to.getX() * borderMultiple);
            to.setZ(to.getZ() * borderMultiple);
        }

        return to;
    }

    private Location getToLocation(PlayerPortalEvent e) {
        Location from = e.getFrom().clone();
        from.setWorld(e.getFrom().getWorld() == UHCWorldUtils.getWorld()
                ? UHCWorldUtils.getNether()
                : UHCWorldUtils.getWorld());

        return from;
    }
}
