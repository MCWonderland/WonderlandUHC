package org.mcwonderland.uhc.scenario.impl.special;

import org.mcwonderland.uhc.api.event.player.UHCPlayerRespawnedEvent;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo open && close
public class ScenarioArmorVsHealth extends ConfigBasedScenario implements Listener {
    private static Map<UHCPlayer, Double> costs = new HashMap<>();
    private static PacketHandler attributeHandler;

    @FilePath(name = "Apply_Within_Seconds_After_Respawned")
    private Integer Apply_Within_Seconds;
    @FilePath(name = "Warn_Msg")
    private List<String> Warn_Msg;

    public ScenarioArmorVsHealth(ScenarioName name) {
        super(name);
    }

    @Override
    protected void onConfigReload() {
        if (attributeHandler == null)
            attributeHandler = new AttributeHandler();
    }

    @EventHandler
    public void onRespawn(UHCPlayerRespawnedEvent e) {
        UHCPlayer uhcPlayer = e.getUhcPlayer();
        Player player = uhcPlayer.getPlayer();

        Chat.send(player, new SimpleReplacer(Warn_Msg).replaceTime(Apply_Within_Seconds).toArray());

        Common.runLater(Apply_Within_Seconds * 20, () -> {
            if (!uhcPlayer.isDead()) {
                costs.remove(uhcPlayer);
                updateHealth(uhcPlayer);
            }
        });
    }

    @Override
    public void onEnable() {
        PacketListenerAPI.addPacketHandler(attributeHandler);
    }

    @Override
    public void onDisable() {
        PacketListenerAPI.removePacketHandler(attributeHandler);
    }

    private class AttributeHandler extends PacketHandler {

        @Override
        public void onSend(SentPacket sentPacket) {
            if (!sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutUpdateAttributes"))
                return;

            Player player = sentPacket.getPlayer();

            if (player != null)
                updateHealth(UHCPlayer.getUHCPlayer(player));
        }

        @Override
        public void onReceive(ReceivedPacket receivedPacket) {

        }
    }

    private void updateHealth(UHCPlayer uhcPlayer) {
        LivingEntity livingEntity = uhcPlayer.getEntity();
        double armorPoints = PlayerUtils.getArmorPoints(livingEntity);
        double cost = getCost(uhcPlayer);

        if (armorPoints > cost)
            Common.runLater(0, () -> {
                double difference = armorPoints - cost;
                costs.put(uhcPlayer, armorPoints);

                Extra.setMaxHealth(livingEntity, Extra.getMaxHealth(livingEntity) - difference);
            });
    }

    private double getCost(UHCPlayer uhcPlayer) {
        return costs.getOrDefault(uhcPlayer, 0D);
    }
}
