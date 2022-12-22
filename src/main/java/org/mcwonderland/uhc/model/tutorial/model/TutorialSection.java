package org.mcwonderland.uhc.model.tutorial.model;

import org.bukkit.entity.Player;
import org.mineacademy.fo.model.BoxedMessage;

public abstract class TutorialSection {

    public static final TutorialSection END_TUTORIAL = null;

    protected void show(Player player) {
        BoxedMessage.tell(player, getMessages());
    }

    public boolean isLastOne() {
        return nextSection() == END_TUTORIAL;
    }

    protected abstract String[] getMessages();

    protected abstract TutorialSection nextSection();
}
