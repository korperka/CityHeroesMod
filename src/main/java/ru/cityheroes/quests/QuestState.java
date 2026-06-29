package ru.cityheroes.quests;

import com.mojang.serialization.Codec;

public enum QuestState {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;

    public static final Codec<QuestState> CODEC =
            Codec.STRING.xmap(
                    QuestState::valueOf,
                    QuestState::name
            );
}
