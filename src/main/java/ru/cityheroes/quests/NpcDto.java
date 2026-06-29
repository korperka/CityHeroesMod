package ru.cityheroes.quests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.world.entity.player.PlayerModelType;

import java.util.List;

@Getter @Setter
public class NpcDto {
    private String id;
    private String displayName;
    private String skinPath;
    private PlayerModelType modelType;
    private List<String> quests;
}
