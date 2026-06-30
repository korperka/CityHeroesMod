package ru.cityheroes.npc;

import lombok.Getter;
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
