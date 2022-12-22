package org.mcwonderland.uhc.hook.packet;

import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.util.GameUtils;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

/**
 * 2019-05-29 上午 11:37
 */
public class SoundController extends PacketHandler {

    @Override
    public void onSend(SentPacket sentPacket) {
        if (Settings.Misc.DISABLE_LOBBY_SOUNDS)
            disableLobbyPlayerSounds(sentPacket);
    }

    @Override
    public void onReceive(ReceivedPacket receivedPacket) {
        if (Settings.Misc.DISABLE_SPECTATOR_HIT_SOUNDS)
            disableSpectatorHitSoundsByCancelAttackPacket(receivedPacket);
    }

    private void disableLobbyPlayerSounds(SentPacket sentPacket) {
        if (IsSoundPacket(sentPacket.getPacketName()) && !GameUtils.isGameStarted()) {
            String sound = formatSoundStringFromPacket(sentPacket);
            if (isHitSounds(sound) || isStepSounds(sound))
                sentPacket.setCancelled(true);
        }
    }

    private boolean IsSoundPacket(String packetName) {
        if (packetName.equals("PacketPlayOutNamedSoundEffect"))
            return true;
        return false;
    }

    private String formatSoundStringFromPacket(SentPacket packet) {
        return "";
    }

    private boolean isHitSounds(String sound) {
        if (sound.contains("entity.player.attack")) {
            return true;
        }
        return false;
    }

    private boolean isStepSounds(String sound) {
        if (sound.contains(".step"))
            return true;
        return false;
    }

    private void disableSpectatorHitSoundsByCancelAttackPacket(ReceivedPacket receivedPacket) {
        if (isAttackPacket(receivedPacket) && isSpectatorOrStaff(receivedPacket.getPlayer())) {
            receivedPacket.setCancelled(true);
        }
    }

    private boolean isAttackPacket(ReceivedPacket receivedPacket) {
        if (receivedPacket.getPacketName().equals("PacketPlayInUseEntity")
                && receivedPacket.getPacketValue(1).toString().equals("ATTACK")) {
            return true;
        }
        return false;
    }

    private boolean isSpectatorOrStaff(Player player) {
        return player != null && UHCPlayer.getUHCPlayer(player).isDead();
    }
}
