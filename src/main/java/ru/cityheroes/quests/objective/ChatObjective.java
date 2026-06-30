package ru.cityheroes.quests.objective;

import static ru.cityheroes.quests.event.QuestEvent.*;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.chat.Component;
import ru.cityheroes.quests.event.QuestEvent;
import ru.cityheroes.quests.objective.interfaces.ImmediateQuestObjective;

@Getter @Setter
public class ChatObjective implements ImmediateQuestObjective<ChatEvent> {
    private String message;
    private boolean ignoreCase;

    @Override
    public Class<ChatEvent> getEventClass() {
        return ChatEvent.class;
    }

    @Override
    public boolean handle(ChatEvent event) {
        return ignoreCase ? message.equalsIgnoreCase(event.message()) : message.equals(event.message());
    }
}
