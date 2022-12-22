package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.model.broadcast.AbstractBroadcastSender;
import org.mcwonderland.uhc.model.broadcast.GameStartTimeConversation;
import org.mcwonderland.uhc.model.broadcast.GameStartTimeInfo;
import org.mcwonderland.uhc.model.broadcast.impl.DiscordBroadcastSender;
import org.mcwonderland.uhc.util.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.exception.FoException;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigClickableButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.menu.config.ItemPath;

public class BroadcastSettingsMenu extends ConfigMenu {

    private final BroadcastButton discordButton;

    public BroadcastSettingsMenu(Menu parent) {
        super(UHCMenuSection.of("Broadcast"), parent);

        discordButton = new BroadcastButton(getButtonPath("Discord"), new DiscordBroadcastSender());
    }


    private class BroadcastButton extends ConfigClickableButton {

        private final AbstractBroadcastSender sender;

        protected BroadcastButton(ItemPath path, AbstractBroadcastSender sender) {
            super(path);

            this.sender = sender;
        }

        @Override
        protected void onClicked(Player player, Menu menu, ClickType click) {
            sender.getDependency().checkSoft();

            new GameStartTimeConversation() {
                @Override
                protected void onStartTimeInfoBuilt(GameStartTimeInfo info) {
                    try {
                        sender.sendBroadcast(info);
                    } catch (FoException e) {
                        Chat.send(player, e.getMessage());
                    }
                }
            }.start(player);
        }
    }

}
