package org.mcwonderland.uhc;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.SneakyThrows;
import me.lulu.datounms.DaTouNMS;
import me.lulu.datounms.UnSupportedNmsException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;
import org.mcwonderland.uhc.api.event.scenario.ScenarioInitEvent;
import org.mcwonderland.uhc.command.TestCommand;
import org.mcwonderland.uhc.command.impl.LeaveCommand;
import org.mcwonderland.uhc.command.impl.game.BackPackCommand;
import org.mcwonderland.uhc.command.impl.game.PracticeCommand;
import org.mcwonderland.uhc.command.impl.game.SendCoordsCommand;
import org.mcwonderland.uhc.command.impl.game.SpecToggleCommand;
import org.mcwonderland.uhc.command.impl.host.*;
import org.mcwonderland.uhc.command.impl.host.whitelist.WhitelistCommandGroup;
import org.mcwonderland.uhc.command.impl.info.*;
import org.mcwonderland.uhc.command.team.TeamCommandGroup;
import org.mcwonderland.uhc.command.uhc.UHCMainCommandGroup;
import org.mcwonderland.uhc.game.Game;
import org.mcwonderland.uhc.game.GameTimerRunnable;
import org.mcwonderland.uhc.game.settings.CacheSaver;
import org.mcwonderland.uhc.game.settings.LoadingStatus;
import org.mcwonderland.uhc.game.settings.UHCGameSettingsSaver;
import org.mcwonderland.uhc.hook.packet.PacketRegister;
import org.mcwonderland.uhc.hook.voice.DiscordVoiceHook;
import org.mcwonderland.uhc.listener.*;
import org.mcwonderland.uhc.menu.ButtonLocalization;
import org.mcwonderland.uhc.model.InvinciblePlayer;
import org.mcwonderland.uhc.model.tutorial.model.TutorialListener;
import org.mcwonderland.uhc.populator.Populator;
import org.mcwonderland.uhc.practice.Practice;
import org.mcwonderland.uhc.practice.SimplePractice;
import org.mcwonderland.uhc.scenario.ScenarioListener;
import org.mcwonderland.uhc.scenario.ScenarioManager;
import org.mcwonderland.uhc.scoreboard.ScoreBoardUpdater;
import org.mcwonderland.uhc.scoreboard.ScoreListener;
import org.mcwonderland.uhc.scoreboard.SidebarTheme;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.settings.Settings;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mcwonderland.uhc.settings.spawn.Spawns;
import org.mcwonderland.uhc.stats.storages.StatsStorage;
import org.mcwonderland.uhc.stats.storages.StatsStorageSql;
import org.mcwonderland.uhc.stats.storages.StatsStorageYaml;
import org.mcwonderland.uhc.util.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.exception.FoException;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.CompSound;

import java.io.File;


public class WonderlandUHC extends SimplePlugin {
    public static Boolean TEST_MODE = false;
    @Getter
    private static StatsStorage statsStorage;
    @Getter
    private ScenarioManager scenarioManager = new ScenarioManager();
    @Getter
    private Practice practice = new SimplePractice();

    //todo
    // 觀察者推礦車
    // redglass border
    // 讓剛購買的客戶永遠能下載最新版本插件
    // reload 後 scenario 會消失
    // generating 時沒有禁止玩家登入，motd 依然顯示開放入場

    public static WonderlandUHC getInstance() {
        return ( WonderlandUHC ) SimplePlugin.getInstance();
    }

    public WonderlandUHC(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file) {
        super(loader, description, dataFolder, file);
    }

    public WonderlandUHC() {
    }

    @Override
    public void onPluginStart() {
        loadFiles();
        NmsLoader.initNms(this);
        registerListeners();
        registerCommands();
        Extra.createHead();
        practice.setup();

        if (Dependency.DISCORD_SRV.isHooked())
            DiscordVoiceHook.setup();

        Common.setTellPrefix("");

        scenarioManager.registerDefaults();
        Common.callEvent(new ScenarioInitEvent());
        checkWorldLoadingStatusAndDoSomeStaff();
        UHCGameSettingsSaver.reloadFromFile();
        logPluginEnabledMessage();
    }


    @Override
    protected void onPluginReload() {
        registerCommandGroups();
        scenarioManager.reloadAll();
        UHCGameSettingsSaver.reloadFromFile();
    }

    @Override
    protected void onReloadablesStart() {
        DependencyChecker.checkDepends();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        modifyFoundationLibraryValues();

        SidebarTheme.loadThemes();
        Spawns.reload();

        loadStatsStorage();

        if (Dependency.CUSTOM_ORE_GENERATOR.isHooked())
            Populator.loadPopulators();

        if (CacheSaver.getLoadingStatus() == LoadingStatus.DONE) {
            PacketRegister.registerPacketListeners();

            ScoreBoardUpdater.start();
            GameTimerRunnable.start();
            InvinciblePlayer.startTask();
        }

        checkTestMode();
    }



    public static void checkTestMode() {
        if (!TEST_MODE)
            return;

        Settings.Game.TIME_TO_START_AFTER_TELEPORT = 10;
        Settings.Game.PRE_START_TIME = 10;
    }

    private void modifyFoundationLibraryValues() {
        ButtonLocalization.load();
        Menu.setSound(new SimpleSound(CompSound.NOTE_STICKS.getSound(), 0, 0));
    }

    private void loadFiles() {
        UHCFiles.getFileNames().forEach(FileUtil::extract);
        FileUtil.extract(UHCFiles.PERMISSIONS, UHCFiles.PERMISSIONS);
    }

    private void loadStatsStorage() {
        if (Settings.Mysql.USE)
            statsStorage = new StatsStorageSql();
        else
            statsStorage = new StatsStorageYaml();
    }

    private void registerListeners() {
        Lists.newArrayList(
                new BooleanEvents(),
                new ChatListener(),
                new TutorialListener(),
                new DamageListener(),
                new InvViewListener(),
                new GameSettingsScenarioListener(this),
                new OldEnchantListener(),
                new StatsListener(),
                new ToolListener(),
                new WorldFillListener(),
                new WorldInitListener(),
                new ScoreListener(),
                new ScenarioListener(this)
        ).forEach(this::registerEvents);
    }

    private void registerCommands() {
        registerCommandGroups();

        Lists.newArrayList(
                new BackPackCommand("backpack|bp"),
                new ConfigCommand("config|cfg"),
                new DisableItemsCommand("disableitems"),
                new GiveAllCommand("giveall"),
                new LeaveCommand("leave"),
                new MLGCommand("mlg"),
                new RespawnCommand("respawn"),
                new ScenariosCommand("scenarios"),
                new SendCoordsCommand("sendcoords|scs"),
                new SetSpawnCommand("setspawn"),
                new BorderCommand("border"),
                new SpecToggleCommand("spectoggle"),
                new StaffCommand("staff"),
                new StatsCommand("stats"),
                new TopKillsCommand("topkills|killtop|kt"),
                new ViewHealCommand("viewheal|h"),
                new PracticeCommand("practice")
        ).forEach(this::registerCommand);


        if (TEST_MODE)
            registerCommand(new TestCommand("test"));
    }

    private void registerCommandGroups() {
        registerCommands(new UHCMainCommandGroup("uhc"));
        registerCommands(new TeamCommandGroup("team"));
        registerCommands(new WhitelistCommandGroup("whitelist|wl"));
    }

    private void logPluginEnabledMessage() {
        Common.logNoPrefix(
                Common.consoleLineSmooth(),
                " _    _                 _           _                 _   _   _ _   _ _____",
                "| |  | |               | |         | |               | | | | | | | | /  __ \\",
                "| |  | | ___  _ __   __| | ___ _ __| | __ _ _ __   __| | | | | | |_| | /  \\/",
                "| |/\\| |/ _ \\| '_ \\ / _` |/ _ \\ '__| |/ _` | '_ \\ / _` | | | | |  _  | |    ",
                "\\  /\\  / (_) | | | | (_| |  __/ |  | | (_| | | | | (_| | | |_| | | | | \\__/\\",
                " \\/  \\/ \\___/|_| |_|\\__,_|\\___|_|  |_|\\__,_|_| |_|\\__,_|  \\___/\\_| |_/\\____/",
                "                                                  ",
                "&3Author: &fLU__LU",
                "&3Version: &f" + getDescription().getVersion(),
                "",
                "&3Enjoy your own UHC time!",
                Common.consoleLineSmooth());
    }

    private void checkWorldLoadingStatusAndDoSomeStaff() {
        LoadingStatus loadingStatus = CacheSaver.getLoadingStatus();
        Game.getGame().setHost(CacheSaver.getHost());

        if (loadingStatus == LoadingStatus.CONFIGURING) {
            BorderUtil.removeUHCWorldWBBorders();
            Extra.deleteWorld(UHCWorldUtils.getWorldName());
            Extra.deleteWorld(UHCWorldUtils.getNetherName());
        } else {
            Game.changeSettings(CacheSaver.getSettings());
            createUhcWorldIfNotExist();
            checkNetherWorld();
            BorderUtil.setInitialBorders();

            if (loadingStatus == LoadingStatus.GENERATING)
                ChunkFiller.fill(UHCWorldUtils.getWorld());
        }
    }

    private void createUhcWorldIfNotExist() {
        World uhcWorld = UHCWorldUtils.getWorld();
        if (uhcWorld == null) {

            Bukkit.createWorld(new WorldCreator(Settings.Game.UHC_WORLD_NAME));
        }
    }

    private void checkNetherWorld() {
        if (UHCWorldUtils.getNether() == null) {
            WorldCreator wc = new WorldCreator(Settings.Game.UHC_WORLD_NAME + "_nether");
            wc.environment(World.Environment.NETHER);
            Bukkit.createWorld(wc);
        }
    }

}
