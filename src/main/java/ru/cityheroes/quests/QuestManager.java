package ru.cityheroes.quests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.dialogs.DialogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestManager {
    @Getter
    private static final Map<String, Quest> quests = new HashMap<>();

    public static Quest getQuestById(String questId) {
        return quests.get(questId);
    }

    public static void loadQuests() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream stream = QuestManager.class.getResourceAsStream(String.format("/assets/%s/quests/quests.json", CityHeroesMod.MOD_ID))) {
            if (stream == null) {
                throw new IllegalStateException("quests.json not found");
            }

            List<Quest> loadedQuests = mapper.readValue(stream, new TypeReference<>() {});
            loadedQuests.forEach(quest -> quests.put(quest.getId(), quest));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
