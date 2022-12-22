package org.mcwonderland.uhc.scenario.impl.block;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.potion.PotionEffectType;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 2019-12-14 上午 11:50
 */
public class ScenarioFastObsidian extends ConfigBasedScenario implements Listener {

    private static final Set<UUID> miningPlayers = new HashSet<>();

    public ScenarioFastObsidian(ScenarioName name) {
        super(name);
    }

    @EventHandler
    public void onArmSwing(PlayerAnimationEvent e) {
        if (e.getAnimationType() == PlayerAnimationType.ARM_SWING)
            checkFastDigging(e.getPlayer());
    }

    private void checkFastDigging(Player player) {
        if (miningPlayers.contains(player.getUniqueId()))
            Extra.potion(player, PotionEffectType.FAST_DIGGING, 2, 3, false);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (e.getBlock().getType() == CompMaterial.OBSIDIAN.getMaterial())
            miningPlayers.add(uuid);
        else
            miningPlayers.remove(uuid);
    }
}
