package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.game.CenterCleaner;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.settings.CommandSettings;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.Sounds;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.Extra;
import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.config.ConfigConfirmMenu;

public class CenterCleanerMenu extends ConfigConfirmMenu {

    private String seed;

    public CenterCleanerMenu(Menu parent) {
        this(parent, null);
    }

    public CenterCleanerMenu(Menu parent, String seed) {
        super(UHCMenuSection.of("Center_Cleaner"), parent);
        this.seed = seed;
    }

    @Override
    protected void onAgree(Player player, Menu menu) {
        Game.getGame().setCenterCleaner(true);

        createWorld(player);
    }

    @Override
    protected void onDisagree(Player player, Menu menu) {
        Game.getGame().setCenterCleaner(false);

        createWorld(player);
    }

    private void createWorld(Player player) {
        player.closeInventory();

        Chat.send(player, CommandSettings.Uhc.Regen.CREATING_WORLD);
        Extra.sound(player, Sounds.Host.START_CREATING_WORLD);

        CenterCleaner.createWorld(Settings.Game.UHC_WORLD_NAME, player, seed);
    }

}
