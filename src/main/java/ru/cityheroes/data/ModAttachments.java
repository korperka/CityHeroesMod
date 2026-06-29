package ru.cityheroes.data;

import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.quests.PlayerQuestData;

@NoArgsConstructor
public final class ModAttachments {
    public static final AttachmentType<PlayerQuestData> QUEST_DATA =
            AttachmentRegistry.createPersistent(
                    CityHeroesMod.id("quests"),
                    PlayerQuestData.CODEC
            );
}