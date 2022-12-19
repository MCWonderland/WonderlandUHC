package org.mcwonderland.uhc.settings;

import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.List;

public class CommandSettings extends AutoLoadStaticConfig {

    private static void init() {
        initSimpleLocalizationValues();
    }

    public static String CANT_EXECUTE_SELF;

    public static void initSimpleLocalizationValues() {
        SimpleLocalization.Commands.COOLDOWN_WAIT = getString("Cooldown_Wait");
        SimpleLocalization.Commands.INVALID_ARGUMENT = getString("Invalid_Argument");
        SimpleLocalization.Commands.INVALID_ARGUMENT_MULTILINE = getString("Invalid_Argument_Multiline");
        SimpleLocalization.Commands.INVALID_SUB_ARGUMENT = getString("Invalid_Sub_Argument");
        SimpleLocalization.Commands.LABEL_DESCRIPTION = getString("Label_Description");
        SimpleLocalization.Commands.LABEL_USAGE = getString("Label_Usage");
        SimpleLocalization.Commands.LABEL_USAGES = getString("Label_Usages");
        SimpleLocalization.Commands.NO_CONSOLE = getString("No_Console");
        SimpleLocalization.Commands.RELOAD_FAIL = getString("Reload_Fail");
        SimpleLocalization.Commands.RELOAD_SUCCESS = getString("Reload_Success");
    }

    @Override
    protected void onLoad() throws Exception {
        loadConfiguration(UHCFiles.COMMANDS);
    }

    public static class Uhc {
        public static class Choose {
            public static List<String> KICK_INIT_MSG;
        }

        public static class Regen {
            public static List<String> CREATING_WORLD;
        }

        public static class SetHost {
            public static String HOST_CHANGED;
        }

        public static class SpecToggle {
            public static String ONLY_FOR_DEFAULT_SPECTATE_MODE;
            public static String GAMEMODE_TOGGLED;
        }
    }

    public static class Whitelist {
        public static String NAME_REQUIRED;
        public static String ADDED, REMOVED;
        public static String ALREADY_ADDED, ALREADY_REMOVED;
        public static String CLEARED;
        public static List<String> LIST;
    }

    public static class GiveAll {
        public static String INVALID_ITEM, INVALID_AMOUNT;
        public static String GIVEN;
    }

    public static class Border {
        public static String ONLY_NUMBER_BETWEEN;
        public static String ONLY_IN_TIMER_MODE;
    }

    public static class Respawn {
        public static String IS_PLAYING;
        public static String BROADCAST;
        public static List<String> RESPAWNED;
    }

    public static class SetSpawn {
        public static String SPAWN_SAVED;
    }

    public static class Heal {
        public static String FORMAT;
    }

    public static class SendCoords {
        public static String FORMAT;
    }

    public static class Team {
        public static String ALREADY_HAS_ONE;
        public static String NOT_OWNER;
        public static String PLAYER_NOT_IN_TEAM;
        public static String PLAYER_HAS_NO_TEAM;
        public static String YOU_DONT_HAVE_TEAM;

        public static class Invite {
            public static String CLICK_HERE;
            public static String ALREADY_IN_YOUR_TEAM;
            public static List<String> INVITED;
            public static List<String> INVITATION_MESSAGES;
        }

        public static class Join {
            public static String NO_INVITATION;
        }

        public static class Kick {
            public static List<String> KICKED;
        }

        public static class Create {
            public static List<String> CREATED;
        }

        public static class Chat {
            public static String CANT_USE;
            public static String JOINED, QUITTED;
        }
    }

    public static class Config {
        public static List<String> MESSAGES;
    }

    public static class TopKills {
        public static List<String> MESSAGES;
    }

    public static class TeamList {
        public static List<String> MESSAGES;
    }

    public static class Mole {
        public static String Scs;
        public static List<String> List;
    }
}
