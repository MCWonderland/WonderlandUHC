package org.mcwonderland.uhc.settings;

import lombok.experimental.UtilityClass;
import org.mineacademy.fo.model.SimpleSound;

public class Sounds extends AutoLoadStaticConfig {

    @Override
    protected void onLoad() throws Exception {
        loadConfiguration(UHCFiles.SOUNDS);
    }

    @UtilityClass
    public static class Commands {
        public SimpleSound SPEC_TOGGLE;
        public SimpleSound RESPAWN;
        public SimpleSound SET_SPAWN;
        public SimpleSound STAFF_ON;
        public SimpleSound STAFF_OFF;
        public SimpleSound TOP_KILLS;
        public SimpleSound TEAM_CHAT_ON;
        public SimpleSound TEAM_CHAT_OFF;
        public SimpleSound TEAM_CREATED;
        public SimpleSound TEAM_INFO;
        public SimpleSound SEND_COORDS;
        public SimpleSound MLG;
    }

    public static class Host {
        public static SimpleSound START_CREATING_WORLD;
        public static SimpleSound WORLD_CREATED;
        public static SimpleSound INVENTORY_EDITED;
        public static SimpleSound GOLDEN_HEAD_CREATED;
        public static SimpleSound CLEAR_ENABLED_SCENARIOS;
        public static SimpleSound SCENARIO_TOGGLED;
    }

    @UtilityClass
    public static class Game {
        public SimpleSound INVINCIBLE_END;
        public SimpleSound DEATH;
        public SimpleSound ITEM_DISABLED;
        public SimpleSound CANT_JOIN_NETHER;
        public SimpleSound WIN;
    }

    @UtilityClass
    public static class Countdown {

        public static class Lobby {
            public static SimpleSound START;
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }

        public static class Start {
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }

        public static class Border {
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }

        public static class Damage {
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }

        public static class Pvp {
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }

        public static class FinalHeal {
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }

        public static class NetherClose {
            public static SimpleSound TICK;
            public static SimpleSound RUN;
        }
    }

    public static class Tutorial {
        public static SimpleSound FINISHED;
        public static SimpleSound NEXT_SECTION;
        public static SimpleSound CANCELLED;
    }

}
