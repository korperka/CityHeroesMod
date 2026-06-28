package ru.cityheroes.client.dialogs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ru.cityheroes.CityHeroesMod;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogManager {
    @Getter
    private static final Map<String, Dialog> dialogs = new HashMap<>();

    public static void loadDialogs() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream stream = DialogManager.class.getResourceAsStream(String.format("/assets/%s/quests/dialogs.json", CityHeroesMod.MOD_ID))) {
            if (stream == null) {
                throw new IllegalStateException("dialogs.json not found");
            }

            List<Dialog> loadedDialogs = mapper.readValue(stream, new TypeReference<>() {});
            loadedDialogs.forEach(dialog -> dialogs.put(dialog.getId(), dialog));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

