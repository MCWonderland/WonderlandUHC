package org.mcwonderland.uhc.scenario;

import org.mineacademy.fo.ChatUtil;

public enum ScenarioName {

    ARMOR_VS_HEALTH,
    ABSORPTION_LESS,
    BACKPACK,
    BENCH_BLITZ,
    BLOOD_DIAMONDS,
    BOW_LESS,
    CUT_CLEAN,
    DAMAGE_DOGERS,
    DIAMOND_LESS,
    DOUBLE_OR_NOTHING,
    FAST_OBSIDIAN,
    FAST_SMELTING,
    FRAGILE_RODS,
    FIRE_LESS,
    FOOD_NEOPHOBIA,
    GOLD_LESS,
    HASTY_BOYS,
    HORSE_LESS,
    IRON_MAN,
    LESS_BOW_DAMAGE,
    LIMITATIONS,
    LUCKY_LEAVES,
    NO_CLEAN,
    NO_ENCHANT,
    NO_FALL,
    POTION_LESS,
    ROD_LESS,
    SHIFT_KILL,
    SILK_WEB,
    SOUP,
    SWAP_INVENTORY,
    SWITCHEROO,
    TIMBER,
    TIME_BOMB,
    TRIPLE_ARROW,
    TRIPLE_ORES,
    VANILLA_PLUS,
    VEIN_MINERS;

    public String capitalize() {
        return ChatUtil.capitalizeFully(name(), new char[]{'_'});
    }
}
