package org.mcwonderland.uhc.model.broadcast;

import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.mineacademy.fo.conversation.SimpleConversation;
import org.mineacademy.fo.conversation.SimplePrompt;

public abstract class GameStartTimeConversation extends SimpleConversation {

    private final GameStartTimeInfo.GameStartTimeInfoBuilder infoBuilder = GameStartTimeInfo.builder();

    @Override
    protected Prompt getFirstPrompt() {
        return new IpPrompt();
    }

    private class IpPrompt extends SimplePrompt {

        @Override
        protected String getPrompt(ConversationContext ctx) {
            return Messages.Editor.Broadcast.IP;
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext ctx, String s) {
            infoBuilder.ip(s);

            return new JoinTimePrompt();
        }
    }

    private class JoinTimePrompt extends SimplePrompt {

        @Override
        protected String getPrompt(ConversationContext ctx) {
            return Messages.Editor.Broadcast.JOIN_TIME;
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext ctx, String s) {
            infoBuilder.joinTime(s);

            return new StartTimePrompt();
        }
    }

    private class StartTimePrompt extends SimplePrompt {

        @Override
        protected String getPrompt(ConversationContext ctx) {
            return Messages.Editor.Broadcast.START_TIME;
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext ctx, String s) {
            infoBuilder.startTime(s);

            return Prompt.END_OF_CONVERSATION;
        }
    }

    @Override
    protected void onConversationEnd(ConversationAbandonedEvent event) {
        if (event.gracefulExit()) {
            onStartTimeInfoBuilt(infoBuilder.build());
        }
    }

    protected abstract void onStartTimeInfoBuilt(GameStartTimeInfo info);
}
