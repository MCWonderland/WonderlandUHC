package org.mcwonderland.uhc.settings;

import org.mcwonderland.uhc.game.SpectateMode;
import org.mcwonderland.uhc.game.teleport.TeleportType;
import org.mcwonderland.uhc.model.freeze.FreezeType;
import org.mcwonderland.uhc.scoreboard.TabHealthType;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.settings.SimpleSettings;
import org.mineacademy.fo.settings.YamlConfigLoader;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 2019-12-17 下午 12:01
 */
public class Settings extends SimpleSettings {

    @Override
    protected int getConfigVersion() {
        return 0;
    }

    @Override
    protected String getSettingsFileName() {
        return UHCFiles.SETTINGS;
    }

    @Override
    protected void preLoad() {
        super.preLoad();
        YamlConfigLoader.load(this.getClass());
    }

    public static String SERIALIZATION;

    public static Boolean SOUNDS;
    public static String BUNGEE_LOBBY;
    public static String RESTART_CMD;

    public static class Border {
        public static Boolean INCLUDE_18_BORDER;
        public static Integer BEDROCK_BORDER_HEIGHT;
        public static Integer BORDER_TIMER_AFTER_FIRST_SHRINK;
        public static TreeSet<Integer> BORDER_TP_SIZES;

        private static void init() {
            setPathPrefix("Border");

            BORDER_TIMER_AFTER_FIRST_SHRINK = Math.max(11, getInteger("Border_Timer_After_First_Shrink"));
            BORDER_TP_SIZES = getStringList("Border_Tp_Sizes")
                    .stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(TreeSet::new));
        }
    }

    public static class Practice {
        public static String WORLD;
        public static Integer WORLD_SIZE;
    }

    public static class Team {
        public static Boolean ALONE_TEAMS;
        public static Boolean GIVE_DEFAULT_TEAM_RANDOM_COLOR;
        public static Boolean CHARACTER_BOLD_DEFAULT;
        public static Boolean TEAMS_ON_TAB;
        public static Integer MAX_CHARACTER_LENGTH;
        public static String DEFAULT_NAME;
    }

    public static class CombatRelog {
        public static Boolean ENABLED;
        public static Boolean RELOG_AFTER_PVP_ENABLED;
        public static Integer RELOG_IN_MINUTES;
    }

    public static class Spectator {
        public static Boolean USE_SPEC_CHAT;
        public static Integer DEATH_KICK_SECONDS;
        public static SpectateMode SPECTATE_MODE;

        private static void init() {
            setPathPrefix("Spectator");

            SPECTATE_MODE = get("Spectate_Mode", SpectateMode.class);
        }
    }

    public static class Misc {
        public static Boolean ALWAYS_DAY;
        public static Boolean NO_FIRE_TICK;
        public static Boolean ANTI_RAIN;
        public static Boolean LOBBY_HIDE;
        public static Boolean DISABLE_LOBBY_SOUNDS;
        public static Boolean DISABLE_SPECTATOR_HIT_SOUNDS;
        public static Boolean NO_CLEAN_ACTION_BAR;
        public static Boolean DEATH_ANIMATION;
        public static Boolean ARROW_HEALTH;
        public static Boolean CHANGE_MOTD;
        public static Boolean USE_BELOW_NAME_HEALTH;
        public static Boolean USE_PLUGIN_DEATH_MESSAGE;
        public static String GOLDEN_HEAD_NAME;
        public static TabHealthType TAB_HEALTH_TYPE;

        private static void init() {
            setPathPrefix("Misc");

            TAB_HEALTH_TYPE = get("Tab_Health_Type", TabHealthType.class);
            GOLDEN_HEAD_NAME = Common.colorize(getString("Golden_Head_Name")); //方便是否為head的判斷
        }
    }

    public static class Game {
        public static Boolean NETHER_BEFORE_PVP;
        public static Integer PRE_START_TIME;
        public static Integer TELEPORT_PLAYERS_DELAY;
        public static Integer TIME_TO_START_AFTER_TELEPORT;
        public static Integer RESPAWN_INVINCIBLE_TIME;
        public static Integer POINT_TP_INVINCIBLE_TIME;
        public static Integer POINT_TP_LOCATIONS;
        public static String UHC_WORLD_NAME;
        public static TeleportType TELEPORT_TYPE;
        public static List<Integer> ANNOUNCES_SECONDS;
        public static FreezeType FREEZE_TYPE;

        private static void init() {
            setPathPrefix("Game");

            FREEZE_TYPE = get("Freeze_Type", FreezeType.class);
            TELEPORT_TYPE = get("Teleport_Mode", TeleportType.class);
            ANNOUNCES_SECONDS = getStringList("Announces_Seconds").stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
        }
    }

    public static class OldEnchant {
        public static Boolean LAPIS;
        public static Boolean HIDE_ENCHANT;
        public static Boolean RANDOM_ENCHANT;
        public static Boolean OLD_ENCHANT_COST;
    }

    public static class CenterCleaner {
        public static Boolean ALLOW_BAD_BIOME;
        public static Integer RANGE;
        public static Integer CHECK_RIVER_IN;
        public static Integer MAX_HIGH;
        public static Integer BAD_BIOME_LIMIT;
        public static String GENERATOR_SETTINGS;
    }

    public static class ChunkLoading {
        public static Integer FREQUENCY;
        public static Integer PADDING;
        public static Boolean FORCE_LOADING_NETHER_CHUNK;
    }

    public static class Mysql {
        public static Boolean USE;
        public static String IP;
        public static Integer PORT;
        public static String DATABASE;
        public static String USERNAME;
        public static String PASSWORD;
        public static String TABLE;
    }

    public static class DiscordVoice {
        public static Boolean USE;
        public static String GUILD_ID;
        public static String VOICE_CATEGORY;
        public static String LOBBY_VOICE;
    }
}
