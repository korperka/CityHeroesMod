package ru.cityheroes.quests.objectives;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import ru.cityheroes.quests.QuestObjective;

@Getter @Setter
public class DestroyItemObjective implements QuestObjective {
    private ItemStack itemStack;

    @Override
    public boolean checkCompleted(Player player) {
        return !player.getInventory().contains(itemStack);
    }
}
