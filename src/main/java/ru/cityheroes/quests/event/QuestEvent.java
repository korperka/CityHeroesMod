package ru.cityheroes.quests.event;

import net.minecraft.server.level.ServerPlayer;

public interface QuestEvent {
    ServerPlayer player();

    record ChatEvent(ServerPlayer player, String message) implements QuestEvent { }
}