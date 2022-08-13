package org.mcwonderland.uhc.settings;

import org.mineacademy.fo.TimeUtil;

import java.util.List;

/**
 * 2019-12-13 上午 11:09
 */
public class Messages extends AutoLoadStaticConfig {
    @Override
    protected void onLoad() throws Exception {
        loadConfiguration(UHCFiles.MESSAGES);
    }

    public static String ENABLED, DISABLED;
    public static String NOT_YET_STARTED;
    public static String USE_ONLY_WHILE_WAITING;
    public static String NOT_GAMING_PLAYER, ONLY_FOR_SPECTATOR;

    public static class Host {
        public static String WHITELIST_ON, WHITELIST_OFF;
        public static String SCENARIO_ENABLED_PLAYER, SCENARIO_DISABLED_PLAYER;
        public static String NETHER_ENABLED_PLAYER, NETHER_DISABLED_PLAYER;
        public static String TEAM_FIRE_ENABLED_PLAYER, TEAM_FIRE_DISABLED_PLAYER;
        public static String BORDER_TYPE_CHANGED;
        public static String WORLD_CREATED, WORLD_DELETED, NOT_GENERATING, WORLD_DOESNT_EXIST;
        public static String GAME_RUNNING;

    }

    public static class Editor {
        public static String CLICK_FINISH;

        public static class Text {
            public static class Title {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class TeamName {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class TeamCharacter {
                public static String MESSAGE;
                public static String SAVED;
                public static String ALREADY_USED;
            }
        }

        public static class Number {
            public static String INVALID_NUMBER;

            public static class MaxPlayers {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class BorderShrinkSpeed {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class BorderSize {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class NetherBorderSize {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class FinalSizeOrShrinkModeBorder {
                public static String MESSAGE;
                public static String SAVED;
            }
        }

        public static class Broadcast {
            public static String IP;
            public static String JOIN_TIME;
            public static String START_TIME;
        }

        public static class Inventory {
            public static String TO_HEAD_FAILED;
            public static String CLICK_TO_HEAD;

            public static class DisableItems {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class CustomDrops {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class CustomInventory {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class PracticeInventory {
                public static String MESSAGE;
                public static String SAVED;
            }
        }

        public static class Time {
            public static String INVALID_TIME;

            public static class Damage {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class FinalHeal {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class Pvp {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class BorderShrink {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class DisableNether {
                public static String MESSAGE;
                public static String SAVED;
            }

            public static class ShrinkCalculator {
                public static String MESSAGE;
                public static String SAVED;
            }
        }
    }

    public static class Lobby {
        public static String PLAYER_JOIN_MSG, PLAYER_LEAVE_MSG;
        public static String AUTOMATIC_GAME_CANCELED;
        public static String NON_SPAWN_SET;
        public static List<String> WELCOME_MSG_CONFIGURING, WELCOME_MSG;
    }

    public static class Kick {
        public static String WAITING_HOST;
        public static String GENERATING;
        public static String WHITELISTED;
        public static String FULL;
        public static String GAME_STARTED;
        public static String THANKS_FOR_PLAYING;
    }

    public static class CenterCleaner {
        public static String RIVER_CENTER;
        public static String BAD_BIOME;
        public static String TOO_HIGH;
    }

    public static class Team {
        public static String FULL_MSG;
        public static String PLAYER_JOINED, PLAYER_LEFT;
        public static String PLAYER_PROMOTED;
        public static String ONLY_IN_CHOSEN_MODE;
    }

    public static class Staff {
        public static String ENABLED, DISABLED;
        public static String MINED_ALERT;
    }

    public static class Spectator {
        public static String NO_PERM_TO_SPEC;
        public static String TELEPORTED_TO_PLAYER;
        public static List<String> DEATH_KICK_MESSAGE;
    }

    public static class CountDown {
        public static String SCATTER_ANNOUNCE, SCATTER_STARTED, SCATTING_PLAYERS, SCATTER_FINISHED;
        public static String DAMAGE_ANNOUNCE, DAMAGE_ENABLED;
        public static String PVP_ANNOUNCE, PVP_ENABLED;
        public static String FINAL_HEAL_ANNOUNCE, FINAL_HEAL_ENABLED;
        public static String NETHER_ANNOUNCE, NETHER_DISABLED;
        public static String STARTING_ANNOUNCE;
        public static List<String> GAME_STARTED;

        public static class Border {

            public static class Timer {
                public static String ANNOUNCE, REDUCED;
            }

            public static class Shrink {
                public static String ANNOUNCE, REDUCED;
            }
        }
    }

    public static class Game {
        public static String PLAYER_DISCONNECT, PLAYER_RECONNECT;
        public static String CANT_JOIN_BEFORE_PVP_ENABLED, NO_NETHER;
        public static String RELOG_DEATH;
        public static String YOU_HAVE_BEEN_KILLED;
        public static String TEAM_ELIMINATED;
        public static String ITEM_DISABLED;
        public static String IPVP_LAVA, IPVP_FIRE;
        public static String ARROW_HEALTH_MESSAGE;
        public static String CLOSING_SERVER_MSG;
        public static List<String> VICTORY_BROADCAST;


        public static class NoClean {
            public static String OBTAINED;
            public static String END;
            public static String ACTION_BAR;
            public static String ACTION_BAR_END;
        }
    }

    public static class ChatFormat {
        public static String TEAM_CHAT;
        public static String SPECTATORS;
        public static String PLAYER;
        public static String STAFF;
    }

    public static class Symbol {
        public static String SECOND, SECONDS;
        public static String MINUTE, MINUTES;

        private static void init() {
            TimeUtil.SECOND_SYMBOL = SECOND;
            TimeUtil.SECONDS_SYMBOL = SECONDS;
            TimeUtil.MINUTE_SYMBOL = MINUTE;
            TimeUtil.MINUTES_SYMBOL = MINUTES;
        }
    }

    public static class Console {
        public static String BIOME_REPLACED, BIOME_NOT_EXIST, CAN_NOT_REPLACE_BIOME;
        public static String CHUNK_LOAD_STARTED, CHUNK_LOAD_FINISHED, CHUNK_LOAD_NETHER_DETECTED, FORCE_NETHER_CHUNK_ON;
    }

    public static class Dependency {
        public static String REQUIRE_DEPENDENCY, REQUIRE_SOFT_DEPENDENCY;
        public static List<String> USING_OLD_WORLD_BORDER_IN_NEW_VERSION;
        public static String CHANGE_TO_OLDER_WORLD_BORDER_VERSION;
    }

    public static class DiscordVoice {
        public static String MOVED;
        public static String RECONNECTING;
        public static String NOT_IN_VOICE;
        public static String REQUIRES_LINKED_ACCOUNT;
    }

    public static class Updater {
        public static String CHECKING_UPDATES, UP_TO_DATE;
        public static List<String> SUCCESS;

        public static class Failed {
            public static List<String> INTERNET, FILE_NOT_FOUND, IO_EXCEPTION;
        }
    }

    public static class Motd {
        public static String CONFIGURING;
        public static String WAITING;
        public static String STARTING;
        public static String PLAYING;
        public static String GENERATING;
    }
}
