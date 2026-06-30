package ru.cityheroes.quests.objective.interfaces;

import ru.cityheroes.quests.event.QuestEvent;

public interface ImmediateQuestObjective<E extends QuestEvent>  extends QuestObjective {
    Class<E> getEventClass();

    boolean handle(E event);
}
