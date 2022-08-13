package org.mcwonderland.uhc.scenario;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.api.Scenario;
import org.mcwonderland.uhc.scenario.impl.block.*;
import org.mcwonderland.uhc.scenario.impl.block.*;
import org.mcwonderland.uhc.scenario.impl.consume.ScenarioAbsorptionLess;
import org.mcwonderland.uhc.scenario.impl.consume.ScenarioFoodNeophobia;
import org.mcwonderland.uhc.scenario.impl.consume.ScenarioPotionLess;
import org.mcwonderland.uhc.scenario.impl.consume.ScenarioSoup;
import org.mcwonderland.uhc.scenario.impl.damage.*;
import org.mcwonderland.uhc.scenario.impl.damage.*;
import org.mcwonderland.uhc.scenario.impl.death.ScenarioNoClean;
import org.mcwonderland.uhc.scenario.impl.death.ScenarioShiftKill;
import org.mcwonderland.uhc.scenario.impl.death.ScenarioSwapInventory;
import org.mcwonderland.uhc.scenario.impl.death.ScenarioTimeBomb;
import org.mcwonderland.uhc.scenario.impl.disable.ScenarioBowLess;
import org.mcwonderland.uhc.scenario.impl.disable.ScenarioHorseLess;
import org.mcwonderland.uhc.scenario.impl.disable.ScenarioNoEnchant;
import org.mcwonderland.uhc.scenario.impl.disable.ScenarioRodLess;
import org.mcwonderland.uhc.scenario.impl.rush.ScenarioCutClean;
import org.mcwonderland.uhc.scenario.impl.rush.ScenarioFastSmelting;
import org.mcwonderland.uhc.scenario.impl.rush.ScenarioHastyBoys;
import org.mcwonderland.uhc.scenario.impl.special.*;
import org.mcwonderland.uhc.scenario.impl.special.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScenarioManager {
    private Map<String, Scenario> scenarios = new HashMap<>();

    public ScenarioManager() {

    }

    public void register(Scenario scenario) {
        scenario.reload();
        this.scenarios.put(scenario.getName(), scenario);
    }

    public void unregister(Scenario scenario) {
        this.scenarios.remove(scenario.getName());
    }

    public void reloadAll() {
        scenarios.forEach((s, scenario) -> {
            scenario.reload();
        });
    }

    public Scenario getScenario(ScenarioName name) {
        return scenarios.get(name.capitalize());
    }

    public Scenario getScenario(String name) {
        return scenarios.get(name);
    }

    public List<Scenario> getEnabledScenarios() {
        return getScenarios().stream()
                .filter(Scenario::isEnabled)
                .collect(Collectors.toList());
    }

    public Collection<Scenario> getScenarios() {
        return scenarios.values();
    }

    public void registerDefaults() {
        Lists.newArrayList(
                new ScenarioArmorVsHealth(ScenarioName.ARMOR_VS_HEALTH),
                new ScenarioAbsorptionLess(ScenarioName.ABSORPTION_LESS),
                new ScenarioBackPack(ScenarioName.BACKPACK),
                new ScenarioBenchBlitz(ScenarioName.BENCH_BLITZ),
                new ScenarioBloodDiamonds(ScenarioName.BLOOD_DIAMONDS),
                new ScenarioBowLess(ScenarioName.BOW_LESS),
                new ScenarioCutClean(ScenarioName.CUT_CLEAN),
                new ScenarioDamageDogers(ScenarioName.DAMAGE_DOGERS),
                new ScenarioDiamondLess(ScenarioName.DIAMOND_LESS),
                new ScenarioDoubleOrNothing(ScenarioName.DOUBLE_OR_NOTHING),
                new ScenarioFastObsidian(ScenarioName.FAST_OBSIDIAN),
                new ScenarioFastSmelting(ScenarioName.FAST_SMELTING),
                new ScenarioFragileRods(ScenarioName.FRAGILE_RODS),
                new ScenarioFireLess(ScenarioName.FIRE_LESS),
                new ScenarioFoodNeophobia(ScenarioName.FOOD_NEOPHOBIA),
                new ScenarioGoldLess(ScenarioName.GOLD_LESS),
                new ScenarioHastyBoys(ScenarioName.HASTY_BOYS),
                new ScenarioHorseLess(ScenarioName.HORSE_LESS),
                new ScenarioIronMan(ScenarioName.IRON_MAN),
                new ScenarioLessBowDamage(ScenarioName.LESS_BOW_DAMAGE),
                new ScenarioLimitations(ScenarioName.LIMITATIONS),
                new ScenarioLuckyLeaves(ScenarioName.LUCKY_LEAVES),
                new ScenarioNoClean(ScenarioName.NO_CLEAN),
                new ScenarioNoEnchant(ScenarioName.NO_ENCHANT),
                new ScenarioNoFall(ScenarioName.NO_FALL),
                new ScenarioPotionLess(ScenarioName.POTION_LESS),
                new ScenarioRodLess(ScenarioName.ROD_LESS),
                new ScenarioShiftKill(ScenarioName.SHIFT_KILL),
                new ScenarioSilkWeb(ScenarioName.SILK_WEB),
                new ScenarioSoup(ScenarioName.SOUP),
                new ScenarioSwapInventory(ScenarioName.SWAP_INVENTORY),
                new ScenarioSwitcheroo(ScenarioName.SWITCHEROO),
                new ScenarioTimber(ScenarioName.TIMBER),
                new ScenarioTimeBomb(ScenarioName.TIME_BOMB),
                new ScenarioTripleArrow(ScenarioName.TRIPLE_ARROW),
                new ScenarioTripleOres(ScenarioName.TRIPLE_ORES),
                new ScenarioVanillaPlus(ScenarioName.VANILLA_PLUS),
                new ScenarioVeinMiners(ScenarioName.VEIN_MINERS)
        ).forEach(this::register);
    }
}
