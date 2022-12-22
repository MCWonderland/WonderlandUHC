package org.mcwonderland.uhc.scenario.impl.special;

import org.mcwonderland.uhc.events.UHCBlockBreakEvent;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.scenario.impl.ConfigBasedScenario;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.collection.StrictMap;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 2019-12-12 上午 11:35
 */
public class ScenarioLimitations extends ConfigBasedScenario implements Listener {

    private static final StrictMap<UUID, OreMined> blockMines = new StrictMap<>();
    private final StrictMap<Material, Integer> limitedBlocks = new StrictMap<>();

    @FilePath(name = "Reached_Limit")
    private String reachedLimitMsg;
    @FilePath(name = "Cant_Mine_More")
    private String cantMineMore;

    @FilePath(name = "Reached_Limit_Sound")
    private SimpleSound reachedLimitSound;
    @FilePath(name = "Cant_Mine_More_Sound")
    private SimpleSound cantMineMoreSound;

    @FilePath(name = "Limits.Diamond")
    private Integer diomandMax;
    @FilePath(name = "Limits.Gold")
    private Integer goldMax;
    @FilePath(name = "Limits.Iron")
    private Integer ironMax;


    public ScenarioLimitations(ScenarioName name) {
        super(name);
    }

    @Override
    protected void onConfigReload() {
        limitedBlocks.clear();
        limitedBlocks.put(CompMaterial.DIAMOND_ORE.getMaterial(), diomandMax);
        limitedBlocks.put(CompMaterial.GOLD_ORE.getMaterial(), goldMax);
        limitedBlocks.put(CompMaterial.IRON_ORE.getMaterial(), ironMax);
    }

    @EventHandler
    protected void onBlockBreak(UHCBlockBreakEvent e) {
        Material blockType = e.getBlockType();

        if (blockHasLimit(blockType)) {
            int limit = limitedBlocks.get(blockType);
            int playerMined = getMineAmount(e.getPlayer(), blockType);

            if (playerMined >= limit)
                dropNothing(e);
            else
                addBlockMines(e.getPlayer(), blockType);
        }
    }

    private boolean blockHasLimit(Material blockType) {
        return limitedBlocks.containsKey(blockType);
    }

    private void dropNothing(UHCBlockBreakEvent e) {
        final Collection<ItemStack> blockDrops = e.getBlock().getDrops();
        blockDrops.forEach(itemStack -> e.replaceDrop(itemStack.getType(), CompMaterial.AIR.getMaterial()));

        tellCantMine(e.getPlayer(), e.getBlockType());
    }

    private void tellCantMine(Player player, Material blockType) {
        Chat.send(player, reachedLimitMsg
                .replace("{amount}", limitedBlocks.get(blockType) + "")
                .replace("{block}", blockType.name())
        );

        Extra.sound(player, cantMineMoreSound);
    }

    private void addBlockMines(Player player, Material blockType) {
        blockMines.get(player.getUniqueId()).addMinedAmount(blockType);

        if (getMineAmount(player, blockType) == limitedBlocks.get(blockType))
            tellReachLimit(player, blockType);

    }

    private void tellReachLimit(Player player, Material blockType) {
        Chat.send(player, cantMineMore
                .replace("{block}", blockType.name())
                .replace("{amount}", limitedBlocks.get(blockType) + ""));

        Extra.sound(player, reachedLimitSound);
    }


    private int getMineAmount(Player player, Material blockType) {
        UUID uuid = player.getUniqueId();

        OreMined oreMined = blockMines.getOrPut(uuid, new OreMined());

        return oreMined.getBlockMined(blockType);
    }

    @Override
    protected SimpleReplacer replaceLore(List<String> list) {
        return super.replaceLore(list)
                .replace("{diamond-limit}", getLimit(CompMaterial.DIAMOND_ORE))
                .replace("{gold-limit}", getLimit(CompMaterial.GOLD_ORE))
                .replace("{iron-limit}", getLimit(CompMaterial.IRON_ORE));
    }

    private int getLimit(CompMaterial compMaterial) {
        return limitedBlocks.get(compMaterial.getMaterial());
    }

    private class OreMined {
        private final StrictMap<Material, Integer> mined = new StrictMap<>();

        public int getBlockMined(Material material) {
            return mined.getOrDefault(material, 0);
        }

        public void addMinedAmount(Material material) {
            int newAmount = getBlockMined(material) + 1;

            mined.override(material, newAmount);
        }
    }
}
