package org.mcwonderland.uhc.command.impl.host;

import org.mcwonderland.uhc.api.enums.RoleName;
import org.mcwonderland.uhc.command.CommandHelper;
import org.mcwonderland.uhc.game.CombatRelog;
import org.mcwonderland.uhc.game.player.UHCPlayer;
import org.mcwonderland.uhc.game.player.UHCPlayers;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.List;

/**
 * 2019-11-17 下午 03:58
 */
public class GiveAllCommand extends SimpleCommand {

    public GiveAllCommand(String name) {
        super(name);

        setMinArguments(2);
        setUsage("<物品類型> <數量>");
        setDescription("給予所有場上的玩家指令數量的物品");
    }

    @Override
    protected void onCommand() {
        CommandHelper.checkGameStarted();

        ItemStack item = getItem();

        giveItemToPlayerAndRelogs(item);

        Chat.broadcast(CommandSettings.GiveAll.GIVEN
                .replace("{amount}", item.getAmount() + "")
                .replace("{material}", item.getType().toString()));
    }

    private ItemStack getItem() {
        CompMaterial material = findMaterial(args[0], CommandSettings.GiveAll.INVALID_ITEM);
        int amount = findNumber(1, CommandSettings.GiveAll.INVALID_AMOUNT);

        return material.toItem(amount);
    }

    private void giveItemToPlayerAndRelogs(ItemStack item) {
        for (UHCPlayer uhcPlayer : UHCPlayers.getStatusIs(RoleName.PLAYER)) {
            ItemStack toAdd = item.clone();
            if (uhcPlayer.isOnline())
                uhcPlayer.getPlayer().getInventory().addItem(toAdd);
            else
                CombatRelog.get(uhcPlayer).addInventoryItem(toAdd);
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1)
            return completeLastWord(CompMaterial.values());

        return super.tabComplete();
    }
}
