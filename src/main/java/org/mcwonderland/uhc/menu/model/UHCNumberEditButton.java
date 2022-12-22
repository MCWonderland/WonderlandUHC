package org.mcwonderland.uhc.menu.model;

import org.mcwonderland.uhc.settings.Messages;
import org.mineacademy.fo.menu.button.config.conversation.ConfigNumberEditButton;
import org.mineacademy.fo.menu.config.ItemPath;

public abstract class UHCNumberEditButton<N extends Number> extends ConfigNumberEditButton<N> {

    public UHCNumberEditButton(ItemPath path) {
        super(path);
    }

    @Override
    protected final String getInvalidNumberMsg() {
        return Messages.Editor.Number.INVALID_NUMBER;
    }
}
