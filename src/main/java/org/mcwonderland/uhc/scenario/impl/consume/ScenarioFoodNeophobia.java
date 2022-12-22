package org.mcwonderland.uhc.scenario.impl.consume;

import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 2019-12-07 下午 03:12
 */
public class ScenarioFoodNeophobia extends ConfigBasedScenario implements Listener {
    private static final Map<UUID, Material> eatenFoodType = new HashMap<>();

    @FilePath(name = "First_Eat")
    private String firstEatMsg;
    @FilePath(name = "Just_Can_Eat")
    private String justCanEatMsg;

    @FilePath(name = "First_Eat_Sound")
    private SimpleSound firstEatSound;
    @FilePath(name = "Just_Can_Eat_Sound")
    private SimpleSound justCanEatSound;


    public ScenarioFoodNeophobia(ScenarioName name) {
        super(name);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemConsume(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        Material itemEat = e.getItem().getType();

        if (isIgnoredItem(itemEat))
            return;

        Material foodEaten = getEatFoodType(player);
        if (foodEaten != null)
            e.setCancelled(!checkEatSame(player, foodEaten, itemEat));
        else
            putFoodData(player, itemEat);
    }

    private boolean checkEatSame(Player player, Material foodEaten, Material itemEat) {
        if (itemEat != foodEaten) {
            Chat.send(player, justCanEatMsg.replace("{foodtype}", foodEaten.name()));
            Extra.sound(player, justCanEatSound);
            return false;
        }
        return true;
    }

    private void putFoodData(Player player, Material itemEat) {
        eatenFoodType.put(player.getUniqueId(), itemEat);
        Chat.send(player, firstEatMsg.replace("{foodtype}", itemEat.name()));
        Extra.sound(player, firstEatSound);
    }

    private Material getEatFoodType(Player player) {
        return eatenFoodType.get(player.getUniqueId());
    }

    private boolean isIgnoredItem(Material material) {
        CompMaterial type = CompMaterial.fromMaterial(material);

        return type == CompMaterial.MILK_BUCKET
                || type == CompMaterial.GOLDEN_APPLE
                || type == CompMaterial.ENCHANTED_GOLDEN_APPLE
                || type == CompMaterial.POTION;
    }
}
