package org.mcwonderland.uhc.game;

public enum StateName {
    WAITING,
    TELEPORTING,
    PRE_START,
    PLAYING;

    public boolean isLobby() {
        return this == WAITING;
    }

    public boolean isTeleport() {
        return this == TELEPORTING || this == PRE_START;
    }

    public boolean isStarted() {
        return this == PLAYING;
    }
}
