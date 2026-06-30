package ru.cityheroes.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import ru.cityheroes.quests.QuestState;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerQuestData {
    private final Map<String, QuestState> quests;
    public static final Codec<PlayerQuestData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.unboundedMap(
                                    Codec.STRING,
                                    QuestState.CODEC
                            ).fieldOf("quests").forGetter(PlayerQuestData::getQuests)
                    ).apply(instance, PlayerQuestData::new)
            );


    public PlayerQuestData() {
        this.quests = new HashMap<>();
    }

    public PlayerQuestData(Map<String, QuestState> quests) {
        this.quests = quests;
    }

    public QuestState getState(String id) {
        return quests.getOrDefault(id, QuestState.NOT_STARTED);
    }

    public void putState(String id, QuestState state) {
        quests.put(id, state);
    }
}