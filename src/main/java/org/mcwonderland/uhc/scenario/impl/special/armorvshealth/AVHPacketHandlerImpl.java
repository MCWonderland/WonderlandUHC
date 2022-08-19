package org.mcwonderland.uhc.scenario.impl.special.armorvshealth;

import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

public class AVHPacketHandlerImpl extends ArmorVsHealthPacketHandler {
    private PacketHandler handler;

    @Override
    void register() {
        handler = new PacketHandler() {
            @Override
            public void onSend(SentPacket sentPacket) {
                if (!sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutUpdateAttributes"))
                    return;

                Player player = sentPacket.getPlayer();

                if (player != null)
                    trigger(player);
            }

            @Override
            public void onReceive(ReceivedPacket receivedPacket) {

            }
        };
        PacketListenerAPI.addPacketHandler(handler);
    }

    @Override
    void unRegister() {
        PacketListenerAPI.removePacketHandler(handler);
    }
}
