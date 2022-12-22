package org.mcwonderland.uhc.command;

import org.mineacademy.fo.command.SimpleCommandGroup;

public abstract class UHCCommandGroup extends SimpleCommandGroup {

    @Override
    protected String getCredits() {
        return "&7歡迎想自己開UHC的玩家加入 https://discord.gg/pJ3RkP5 購買插件。";
    }
}
