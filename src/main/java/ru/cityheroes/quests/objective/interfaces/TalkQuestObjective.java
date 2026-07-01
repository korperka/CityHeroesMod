package ru.cityheroes.quests.objective.interfaces;

import net.minecraft.world.entity.player.Player;

public interface TalkQuestObjective extends QuestObjective {
    boolean checkCompleted(Player player);
}
