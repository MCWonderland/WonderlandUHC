package org.mcwonderland.uhc.hook.packet;

import org.inventivetalent.packetlistener.PacketListenerAPI;

/**
 * 2019-06-26 下午 07:38
 */
public class PacketRegister {
    public static void registerPacketListeners() {
        PacketListenerAPI.addPacketHandler(new SoundController());
    }
}