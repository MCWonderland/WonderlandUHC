package org.mcwonderland.uhc.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class PotionApplier {

    public static void addPotionEffect(Player player, PotionEffect effect) {
        PotionEffect remainingEffect = getPotionEffect(player, effect.getType());

        if (remainingEffect == null) {
            player.addPotionEffect(effect);
            return;
        }

        if (isHaveToGive(remainingEffect, effect)) {
            player.removePotionEffect(effect.getType());
            player.addPotionEffect(effect);
        }
    }

    private static PotionEffect getPotionEffect(Player player, PotionEffectType type) {
        Collection<PotionEffect> effects = player.getActivePotionEffects();

        for (PotionEffect effect : effects) {
            if (effect.getType().equals(type))
                return effect;
        }

        return null;
    }

    private static boolean isHaveToGive(PotionEffect remainingEffect, PotionEffect toGiveEffect) {
        boolean levelLower = remainingEffect.getAmplifier() < toGiveEffect.getAmplifier();
        boolean levelSame = remainingEffect.getAmplifier() == toGiveEffect.getAmplifier();
        boolean durationShorter = remainingEffect.getDuration() < toGiveEffect.getDuration();

        return levelLower || (levelSame && durationShorter);
    }
}
