package ru.cityheroes.quests.objective;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.cityheroes.quests.objective.interfaces.TalkQuestObjective;

@Getter @Setter
public class ObtainItemObjective implements TalkQuestObjective {
    private String item;
    private int count;

    @Override
    public boolean checkCompleted(Player player) {
        int remaining = count;
        Item toRemove = BuiltInRegistries.ITEM.get(Identifier.parse(item)).orElseThrow().value();

        if(player.getInventory().countItem(toRemove) < count) return false;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (!stack.isEmpty() && stack.is(toRemove)) {
                int take = Math.min(remaining, stack.getCount());

                stack.shrink(take);
                remaining -= take;

                if (remaining <= 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
