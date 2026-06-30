package ru.cityheroes.npc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.quests.QuestManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NpcManager {
    @Getter
    private static final Map<String, NpcDto> npcs = new HashMap<>();

    public static NpcDto getNpc(String id) {
        return npcs.get(id);
    }

    public static List<String> getQuestsByNpc(String npcId) {
        return npcs.get(npcId).getQuests();
    }

    public static void loadNpcs() {
        npcs.clear();
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream stream = QuestManager.class.getResourceAsStream(String.format("/assets/%s/quests/npcs.json", CityHeroesMod.MOD_ID))) {
            if (stream == null) {
                throw new IllegalStateException("npcs.json not found");
            }

            List<NpcDto> loadedNpcs = mapper.readValue(stream, new TypeReference<>() {});
            loadedNpcs.forEach(npc -> npcs.put(npc.getId(), npc));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

