package org.mcwonderland.uhc.menu.impl.host;

import org.mcwonderland.uhc.Dependency;
import org.mcwonderland.uhc.WonderlandUHC;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.game.settings.UHCGameSettings;
import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mcwonderland.uhc.menu.model.InventoryEditButton;
import org.mcwonderland.uhc.menu.model.UHCNumberEditButton;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.Chat;
import org.mcwonderland.uhc.util.InventorySaver;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.config.ConfigClickableButton;
import org.mineacademy.fo.menu.button.config.ConfigMenuButton;
import org.mineacademy.fo.menu.button.config.ConfigOpenMenuButton;
import org.mineacademy.fo.menu.button.config.conversation.ConfigEditorButton;
import org.mineacademy.fo.menu.button.config.value.ConfigBooleanButton;
import org.mineacademy.fo.menu.button.config.value.ConfigChangeValueButton;
import org.mineacademy.fo.menu.config.ConfigMenu;
import org.mineacademy.fo.model.SimpleReplacer;

public class MainSettingsMenu extends ConfigMenu {

    private final ConfigMenuButton appleRatesButton;
    private final ConfigMenuButton borderButton;
    private final ConfigMenuButton broadcastButton;
    private final ConfigMenuButton customDropsButton;
    private final ConfigMenuButton customInventoryButton;
    private final ConfigMenuButton practiceInventoryButton;
    private final ConfigMenuButton disableItemsButton;
    private final ConfigMenuButton editTitleButton;
    private final ConfigMenuButton enderPearlDamageButton;
    private final ConfigMenuButton generatorButton;
    private final ConfigMenuButton netherButton;
    private final ConfigMenuButton playersButton;
    private final ConfigMenuButton savesButton;
    private final ConfigMenuButton scenariosButton;
    private final ConfigMenuButton scoreboardButton;
    private final ConfigMenuButton startExpButton;
    private final ConfigMenuButton startOrGenerateButton;
    private final ConfigMenuButton teamButton;
    private final ConfigMenuButton timeButton;
    private final ConfigMenuButton whitelistButton;


    public MainSettingsMenu(Menu parent) {
        super(UHCMenuSection.of("Main"), parent);

        teamButton = new ConfigOpenMenuButton(getButtonPath("Team"), TeamModeSettingsMenu.class);
        borderButton = new ConfigOpenMenuButton(getButtonPath("Border"), BorderSettingsMenu.class);
        timeButton = new ConfigOpenMenuButton(getButtonPath("Time"), TimeSettingsMenu.class);
        scoreboardButton = new ConfigOpenMenuButton(getButtonPath("Scoreboard"), ScoreboardSettingsMenu.class);
        broadcastButton = new ConfigOpenMenuButton(getButtonPath("Broadcast"), BroadcastSettingsMenu.class);

        UHCGameSettings settings = Game.getSettings();
        WonderlandUHC plugin = WonderlandUHC.getInstance();

        scenariosButton = new ConfigClickableButton(getButtonPath("Scenarios")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                new ScenarioSettingsMenu(menu, plugin.getScenarioManager()).displayTo(player);
            }
        };

        whitelistButton = new ConfigBooleanButton(getButtonPath("Whitelist")) {
            @Override
            protected void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
                settings.setWhitelistOn(newStatus);

                broadcast(newStatus ? Messages.Host.WHITELIST_ON : Messages.Host.WHITELIST_OFF, player);
            }

            @Override
            protected boolean getBoolean() {
                return settings.isWhitelistOn();
            }
        };

        playersButton = new UHCNumberEditButton<Integer>(getButtonPath("Players")) {
            @Override
            public Integer getOriginalNumber() {
                return settings.getMaxPlayers();
            }

            @Override
            protected void handleNumber(Integer newValue) {
                settings.setMaxPlayers(newValue);
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Number.MaxPlayers.MESSAGE;
            }

            @Override
            protected String getSavedMessage(String input) {
                return Messages.Editor.Number.MaxPlayers.SAVED;
            }
        };

        generatorButton = new ConfigClickableButton(getButtonPath("Generator")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                Dependency.CUSTOM_ORE_GENERATOR.checkSoft();

                new GeneratorSettingsMenu(menu).displayTo(player);
            }
        };

        customInventoryButton = new InventoryEditButton(getButtonPath("Custom_Inventory")) {
            @Override
            protected InventorySaver.SaveType getSaveType() {
                return InventorySaver.SaveType.CUSTOM_INVENTORY;
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Inventory.CustomInventory.MESSAGE;
            }

            @Override
            protected String getSavedMessage() {
                return Messages.Editor.Inventory.CustomInventory.SAVED;
            }
        };

        practiceInventoryButton = new InventoryEditButton(getButtonPath("Practice_Inventory")) {
            @Override
            protected InventorySaver.SaveType getSaveType() {
                return InventorySaver.SaveType.PRACTICE_INVENTORY;
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Inventory.PracticeInventory.MESSAGE;
            }

            @Override
            protected String getSavedMessage() {
                return Messages.Editor.Inventory.PracticeInventory.SAVED;
            }
        };

        customDropsButton = new InventoryEditButton(getButtonPath("Custom_Drops")) {
            @Override
            protected InventorySaver.SaveType getSaveType() {
                return InventorySaver.SaveType.CUSTOM_DROPS;
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Inventory.CustomDrops.MESSAGE;
            }

            @Override
            protected String getSavedMessage() {
                return Messages.Editor.Inventory.CustomDrops.SAVED;
            }
        };

        appleRatesButton = new ConfigChangeValueButton(getButtonPath("Apple_Rate"), 1) {
            @Override
            protected void onChangingSize(Player player, Menu menu, int newCount) {
                settings.setAppleRate(newCount);
            }

            @Override
            protected int getOriginalSize() {
                return settings.getAppleRate();
            }

            @Override
            protected Integer getMaximum() {
                return 100;
            }
        };

        startExpButton = new ConfigChangeValueButton(getButtonPath("Experience"), 1) {

            @Override
            protected void onChangingSize(Player player, Menu menu, int newCount) {
                settings.setInitialExperience(newCount);
            }

            @Override
            protected int getOriginalSize() {
                return settings.getInitialExperience();
            }
        };

        editTitleButton = new ConfigEditorButton(getButtonPath("Title")) {
            @Override
            protected void sendPrompt() {
                Chat.sendConversing(getPlayer(), Messages.Editor.Text.Title.MESSAGE);
            }

            @Override
            protected void onEdit(String s) {
                String newTitle = Common.colorize(s);
                settings.setTitle(newTitle);

                String broadcastMessage = Messages.Editor.Text.Title.SAVED.replace("{title}", newTitle);

                broadcast(broadcastMessage, getPlayer());
                Chat.sendConversing(getPlayer(), broadcastMessage);
            }

            @Override
            protected SimpleReplacer replaceLore(SimpleReplacer unReplacedLore) {
                return unReplacedLore.replace("{title}", settings.getTitle());
            }
        };

        netherButton = new ConfigBooleanButton(getButtonPath("Nether")) {
            @Override
            protected void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
                settings.setUsingNether(newStatus);

                broadcast(newStatus ? Messages.Host.NETHER_ENABLED_PLAYER : Messages.Host.NETHER_DISABLED_PLAYER, player);
            }

            @Override
            protected boolean getBoolean() {
                return settings.isUsingNether();
            }
        };

        disableItemsButton = new InventoryEditButton(getButtonPath("Disable_Items")) {
            @Override
            protected InventorySaver.SaveType getSaveType() {
                return InventorySaver.SaveType.DISABLE_ITEMS;
            }

            @Override
            protected String getMessage() {
                return Messages.Editor.Inventory.DisableItems.MESSAGE;
            }

            @Override
            protected String getSavedMessage() {
                return Messages.Editor.Inventory.DisableItems.SAVED;
            }
        };

        enderPearlDamageButton = new ConfigBooleanButton(getButtonPath("Ender_Pearl_Damage")) {
            @Override
            protected void onStatusChange(Player player, Menu menu, ClickType click, boolean newStatus) {
                settings.setEnderPearlDamage(newStatus);
            }

            @Override
            protected boolean getBoolean() {
                return settings.isEnderPearlDamage();
            }
        };


        savesButton = new ConfigClickableButton(getButtonPath("Saves")) {
            @Override
            protected void onClicked(Player player, Menu menu, ClickType click) {
                new SavedSettingsMenu(menu, player).displayTo(player);
            }
        };

        startOrGenerateButton = getStartOrGenButton();
    }

    private ConfigMenuButton getStartOrGenButton() {
        ConfigMenuButton button;

        if (CacheSaver.getLoadingStatus() != LoadingStatus.DONE)
            button = new ConfigOpenMenuButton(getButtonPath("Generate_Map"), CenterCleanerMenu.class);
        else
            button = new ConfigClickableButton(getButtonPath("Start")) {
                @Override
                protected void onClicked(Player player, Menu menu, ClickType click) {
                    Game.getGame().tryToStart(player);
                    player.closeInventory();
                }
            };

        return button;
    }
}
