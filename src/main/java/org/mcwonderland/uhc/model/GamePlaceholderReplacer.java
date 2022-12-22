package org.mcwonderland.uhc.model;

import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.api.Scenario;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.game.settings.sub.UHCItemSettings;
import org.mcwonderland.uhc.game.settings.sub.UHCTimerSettings;
import org.mcwonderland.uhc.scenario.ScenarioManager;
import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.model.SimpleReplacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 2019-11-21 下午 04:20
 */
public class GamePlaceholderReplacer extends SimpleReplacer {
    private final UHCGameSettings settings;

    private GamePlaceholderReplacer(List<String> messages, UHCGameSettings settings) {
        super(messages);

        this.settings = settings;
    }

    public static List<String> replace(List<String> list) {
        return replace(list, Game.getSettings());
    }

    public static List<String> replace(List<String> list, UHCGameSettings settings) {
        List<String> toReplace = new ArrayList<>(list);

        GamePlaceholderReplacer replacer = new GamePlaceholderReplacer(toReplace, settings);

        return replacer.replacePlaceholders();
    }

    public List<String> replacePlaceholders() {
        replaceCommons();
        replaceTimers();
        replaceGameSettings();
        replaceScenarios();
        replaceItems();

        return buildList();
    }


    private void replaceCommons() {
        replace("{host}", Game.getGame().getHost());
        replace("{title}", settings.getTitle());
        replace("{team-size}", settings.getTeamSettings().getTeamSize());
    }

    private void replaceTimers() {
        UHCTimerSettings timer = settings.getTimer();

        replace("{invisible-time}", formatTime(timer.getDamageTime()));
        replace("{finalheal-time}", formatTime(timer.getHealTime()));
        replace("{pvp-time}", formatTime(timer.getPvpTime()));
        replace("{disable-nether-time}", formatTime(timer.getDisableNetherTime()));
        replace("{firstborder-time}", formatTime(timer.getBorderShrinkTime()));
    }

    private void replaceGameSettings() {
        replace("{border-size}", settings.getBorderSettings().getInitialBorder());
        replace("{apple-rate}", settings.getAppleRate());
        replace("{nether-on}", settings.isUsingNether());
        replace("{start-level}", settings.getInitialExperience());
        replace("{friendly-fire}", settings.getTeamSettings().isAllowTeamFire());
        replace("{enderpearl-damage}", settings.isEnderPearlDamage());
        replace("{initial-xp}", settings.getInitialExperience());
    }

    private void replaceScenarios() {
        StringBuilder builder = new StringBuilder();
        ScenarioManager scenarioManager = WonderlandUHC.getInstance().getScenarioManager();

        settings.getScenarios().forEach(s -> {
            Scenario scenario = scenarioManager.getScenario(s);

            if (scenario != null)
                builder.append("\n  - " + scenario.getFancyName());
        });

        replace("{scenarios}", builder.toString());
    }

    private void replaceItems() {
        UHCItemSettings itemSettings = settings.getItemSettings();

        replace("{start-items}", formatItems(itemSettings.getCustomInventoryItems().getAllItems()));
        replace("{custom-drops}", formatItems(itemSettings.getCustomDrops()));
        replace("{disabled-items}", "disableitems");
    }

    @Override
    public SimpleReplacer replace(String from, Object to) {
        return super.replace(from, ((to instanceof Boolean) ? replaceBoolean(( Boolean ) to) : to));
    }

    private String formatTime(Integer time) {
        return TimeUtil.formatTime(time);
    }

    private String formatItems(ItemStack[]... itemStacks) {
        List<ItemStack> list = new ArrayList<>();

        for (ItemStack[] itemStackArr : itemStacks) {
            if (itemStackArr != null)
                list.addAll(Arrays.asList(itemStackArr));
        }

        return formatItems(list);
    }

    private String formatItems(List<ItemStack> itemStacks) {
        return itemStacks.stream()
                .filter(itemStack -> itemStack != null && itemStack.getItemMeta() != null)
                .map(itemStack -> "\n   - " + formatItemStack(itemStack))
                .collect(Collectors.joining());
    }

    private String formatItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        String name = meta.getDisplayName();
        Material type = itemStack.getType();

        String finalName = (name == null || name.isEmpty()) ? ItemUtil.bountifyCapitalized(type) : name;

        return finalName + " x" + itemStack.getAmount();
    }


    private String replaceBoolean(boolean bool) {
        return bool ? Messages.ENABLED : Messages.DISABLED;
    }
}
