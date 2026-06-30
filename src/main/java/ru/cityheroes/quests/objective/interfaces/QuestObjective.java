package ru.cityheroes.quests.objective.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.cityheroes.quests.objective.ChatObjective;
import ru.cityheroes.quests.objective.DestroyItemObjective;
import ru.cityheroes.quests.objective.ObtainItemObjective;
import ru.cityheroes.quests.objective.WriteNameObjective;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ObtainItemObjective.class, name = "obtain_item"),
        @JsonSubTypes.Type(value = DestroyItemObjective.class, name = "destroy_item"),
        @JsonSubTypes.Type(value = ChatObjective.class, name = "chat_message"),
        @JsonSubTypes.Type(value = WriteNameObjective.class, name = "write_name")
})
public interface QuestObjective {
}
