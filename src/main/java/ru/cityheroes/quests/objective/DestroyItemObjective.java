package ru.cityheroes.quests.objective;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import ru.cityheroes.quests.objective.interfaces.TalkQuestObjective;

@Getter @Setter
public class DestroyItemObjective implements TalkQuestObjective {
    private String item;

    @Override
    public boolean checkCompleted(Player player) {
        Item toRemove = BuiltInRegistries.ITEM.get(Identifier.parse(item)).orElseThrow().value();
        return player.getInventory().countItem(toRemove) == 0;
    }
}
