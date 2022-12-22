package org.mcwonderland.uhc.command.impl;

import org.mineacademy.fo.command.SimpleCommand;

/**
 * 2019-11-27 下午 01:05
 */
public class EmptyCommand extends SimpleCommand {

    public EmptyCommand(String label) {
        super(label);

        setMinArguments(0);
        setUsage("<>");
        setDescription("");
    }

    @Override
    protected void onCommand() {
        
    }
}