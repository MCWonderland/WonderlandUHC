package org.mcwonderland.uhc.menu.impl;

import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.model.InventoryContent;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.button.config.ConfigDummyButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.menu.model.InventoryDrawer;
import org.mineacademy.fo.model.SimpleReplacer;


public class InventoryViewer extends ConfigMenu {

    private final Player target;
    private final ConfigDummyButton healthInfo, hungerInfo, levelInfo;

    public InventoryViewer(Player target) {
        super(UHCMenuSection.of("See_Inventory"), null);

        setTitle(getTitle().replace("{player}", target.getName()));

        this.target = target;

        healthInfo = new ConfigDummyButton(getButtonPath("Health")) {
            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{health}", Extra.formatHealth(target.getHealth()));
            }
        };

        hungerInfo = new ConfigDummyButton(getButtonPath("Hunger")) {
            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{hunger}", target.getFoodLevel());
            }
        };

        levelInfo = new ConfigDummyButton(getButtonPath("Level")) {
            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return super.replaceLore(unReplacedLore)
                        .replace("{level}", target.getLevel());
            }
        };
    }

    @Override
    protected void onDisplay(InventoryDrawer drawer) {
        renderInventoryContents(drawer);
    }

    private void renderInventoryContents(InventoryDrawer drawer) {
        for (ItemStack content : InventoryContent.contentsOf(target))
            drawer.pushItem(content);
    }
}

