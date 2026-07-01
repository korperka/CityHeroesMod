package ru.cityheroes.quests.objective;

import static ru.cityheroes.quests.event.QuestEvent.*;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.phys.Vec3;
import ru.cityheroes.dto.Vec3Dto;
import ru.cityheroes.quests.event.QuestEvent;
import ru.cityheroes.quests.objective.interfaces.ImmediateQuestObjective;

@Getter @Setter
public class ReachLocationObjective implements ImmediateQuestObjective<MoveEvent> {
    private Vec3Dto location;
    private int radius;

    @Override
    public Class<QuestEvent.MoveEvent> getEventClass() {
        return MoveEvent.class;
    }

    @Override
    public boolean handle(MoveEvent event) {
        return location.toVec3().distanceTo(event.currentLocation()) <= radius;
    }
}
