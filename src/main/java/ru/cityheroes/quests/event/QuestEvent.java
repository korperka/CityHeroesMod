package ru.cityheroes.quests.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public interface QuestEvent {
    ServerPlayer player();

    record ChatEvent(ServerPlayer player, String message) implements QuestEvent { }
    record MoveEvent(ServerPlayer player, Vec3 lastLocation, Vec3 currentLocation) implements QuestEvent { }
}