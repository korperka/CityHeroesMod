package ru.cityheroes.quests.objective;

import lombok.Getter;
import lombok.Setter;
import ru.cityheroes.data.ModAttachments;
import ru.cityheroes.data.PlayerQuestData;
import ru.cityheroes.quests.event.QuestEvent;
import ru.cityheroes.quests.objective.interfaces.ImmediateQuestObjective;

@Getter @Setter
public class WriteNameObjective implements ImmediateQuestObjective<QuestEvent.ChatEvent> {
    @Override
    public Class<QuestEvent.ChatEvent> getEventClass() {
        return QuestEvent.ChatEvent.class;
    }

    @Override
    public boolean handle(QuestEvent.ChatEvent event) {
        PlayerQuestData data = event.player().getAttached(ModAttachments.QUEST_DATA);
        if(data == null) return false;

        data.setName(event.message());

        return true;
    }
}