package ru.cityheroes.quests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.minecraft.world.entity.player.Player;
import ru.cityheroes.quests.objectives.DestroyItemObjective;
import ru.cityheroes.quests.objectives.ObtainItemObjective;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ObtainItemObjective.class, name = "obtain_item"),
        @JsonSubTypes.Type(value = DestroyItemObjective.class, name = "destroy_item")
})
public interface QuestObjective {
    boolean checkCompleted(Player player);
}
