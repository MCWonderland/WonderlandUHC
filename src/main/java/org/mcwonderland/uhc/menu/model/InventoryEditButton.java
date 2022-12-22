package org.mcwonderland.uhc.menu.model;

import lombok.RequiredArgsConstructor;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.mcwonderland.uhc.util.InventorySaver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.mineacademy.fo.menu.button.config.conversation.ConfigInventoryEditorButton;
import org.mineacademy.fo.menu.config.ItemPath;
import org.mineacademy.fo.model.SimpleComponent;
import org.mineacademy.fo.remain.CompMaterial;

public abstract class InventoryEditButton extends ConfigInventoryEditorButton {
    private final String TO_HEAD_INPUT = "tohead";

    private final InventorySaver.SaveType saveType;

    protected InventoryEditButton(ItemPath path) {
        super(path);

        this.saveType = getSaveType();
    }

    protected abstract InventorySaver.SaveType getSaveType();

    @Override
    protected void loadInventory(PlayerInventory playerInventory) {
        InventorySaver.setContents(getPlayer(), saveType);
    }

    @Override
    protected void onStart() {
        Player player = getPlayer();

        Chat.sendConversing(player, getMessage());
        Chat.sendConversing(player, "");
        tellComponents(player);
    }

    protected abstract String getMessage();

    protected void tellComponents(Player player) {
        SimpleComponent
                .of(Messages.Editor.Inventory.CLICK_TO_HEAD)
                .onClickRunCmd(TO_HEAD_INPUT)
                .send(player);

        SimpleComponent
                .of(Messages.Editor.CLICK_FINISH)
                .onClickRunCmd(FINISH_INPUT)
                .send(player);
    }

    @Override
    protected boolean isInputValid(String input) {
        switch (input) {
            case TO_HEAD_INPUT:
                GoldenHeadChanger.changeHandItemToHead(getPlayer());
                return false;
        }

        return super.isInputValid(input);
    }


    @Override
    public void onSave(PlayerInventory playerInventory) {
        Player player = getPlayer();

        Chat.sendConversing(player, getSavedMessage());
        InventorySaver.saveInventoryData(player, saveType);
        Extra.sound(player, Sounds.Host.INVENTORY_EDITED);
    }

    protected abstract String getSavedMessage();

    @RequiredArgsConstructor
    private static class GoldenHeadChanger {
        private final Player player;

        private static void changeHandItemToHead(Player player) {
            new GoldenHeadChanger(player).change();
        }

        private void change() {
            if (player.getItemInHand().getType() == CompMaterial.GOLDEN_APPLE.getMaterial())
                changeGoldenAppleToGoldenHead();
            else
                Chat.sendConversing(player, Messages.Editor.Inventory.TO_HEAD_FAILED);
        }

        private void changeGoldenAppleToGoldenHead() {
            ItemMeta meta = player.getItemInHand().getItemMeta();
            meta.setDisplayName(Settings.Misc.GOLDEN_HEAD_NAME);
            player.getItemInHand().setItemMeta(meta);
            Extra.sound(player, Sounds.Host.GOLDEN_HEAD_CREATED);
        }
    }
}
