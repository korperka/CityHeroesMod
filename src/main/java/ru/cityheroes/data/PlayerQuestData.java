package ru.cityheroes.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import ru.cityheroes.quests.QuestState;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerQuestData {
    private final Map<String, QuestState> quests;
    @Setter
    private String name;
    public static final Codec<PlayerQuestData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Codec.unboundedMap(
                                    Codec.STRING,
                                    QuestState.CODEC
                            ).fieldOf("quests").forGetter(PlayerQuestData::getQuests),

                            Codec.STRING
                                    .optionalFieldOf("name", "")
                                    .forGetter(PlayerQuestData::getName)
                    ).apply(instance, PlayerQuestData::new)
            );

    public PlayerQuestData() {
        this(new HashMap<>(), "");
    }

    public PlayerQuestData(Map<String, QuestState> quests, String name) {
        this.quests = quests;
        this.name = name;
    }

    public QuestState getState(String id) {
        return quests.getOrDefault(id, QuestState.NOT_STARTED);
    }

    public void putState(String id, QuestState state) {
        quests.put(id, state);
    }
}