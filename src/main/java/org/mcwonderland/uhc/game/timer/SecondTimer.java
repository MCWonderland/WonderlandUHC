package org.mcwonderland.uhc.game.timer;

public abstract class SecondTimer implements Timer {

    @Override
    public int runTick() {
        return 20;
    }
}
