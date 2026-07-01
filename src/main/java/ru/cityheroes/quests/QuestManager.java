package ru.cityheroes.quests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.data.ModAttachments;
import ru.cityheroes.data.PlayerQuestData;
import ru.cityheroes.packet.SyncToastsPayload;
import ru.cityheroes.packet.ShowToastPayload;
import ru.cityheroes.quests.event.QuestEvent;
import ru.cityheroes.quests.objective.interfaces.ImmediateQuestObjective;
import ru.cityheroes.quests.objective.interfaces.QuestObjective;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestManager {
    @Getter
    private static final Map<String, Quest> quests = new HashMap<>();
    @Getter
    private static final Map<String, Quest> questByDialogs = new HashMap<>();

    public static Quest getQuestById(String questId) {
        return quests.get(questId);
    }

    public static Quest getQuestByDialog(String dialogId) {
        return questByDialogs.get(dialogId);
    }

    public static void loadQuests() {
        quests.clear();
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream stream = QuestManager.class.getResourceAsStream(String.format("/assets/%s/quests/quests.json", CityHeroesMod.MOD_ID))) {
            if (stream == null) {
                throw new IllegalStateException("quests.json not found");
            }

            List<Quest> loadedQuests = mapper.readValue(stream, new TypeReference<>() {});
            loadedQuests.forEach(quest -> {
                quest.getDialogs().values().forEach(id -> questByDialogs.put(id, quest));
                quests.put(quest.getId(), quest);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E extends QuestEvent> void fire(E event) {
        ServerPlayer player = event.player();
        PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
        if (data == null) return;

        for (String questId : data.getQuests().keySet()) {
            if (data.getState(questId) != QuestState.IN_PROGRESS) {
                continue;
            }

            Quest quest = getQuestById(questId);
            QuestObjective objective = quest.getObjective();

            if (!(objective instanceof ImmediateQuestObjective<?> immediate)) {
                continue;
            }

            if (immediate.getEventClass() != event.getClass()) {
                continue;
            }

            if (handle(immediate, event)) {
                data.putState(questId, QuestState.COMPLETED);

                applyAssignedQuest(player, quest);
                syncToasts(player);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends QuestEvent> boolean handle(
            ImmediateQuestObjective<?> objective,
            E event
    ) {
        return ((ImmediateQuestObjective<E>) objective).handle(event);
    }

    public static void registerHandler() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, player, params) ->
                QuestManager.fire(new QuestEvent.ChatEvent(player, message.signedContent()))
        );
    }

    public static void sendToast(ServerPlayer player, Quest quest) {
        ServerPlayNetworking.send(player, new ShowToastPayload("Активное задание: ", quest.getName(), quest.getId()));
    }

    public static void syncToasts(ServerPlayer player) {
        PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
        if (data == null) return;

        List<String> ids = data.getQuests().entrySet().stream()
                .filter(e -> e.getValue() == QuestState.IN_PROGRESS)
                .map(Map.Entry::getKey)
                .toList();

        ServerPlayNetworking.send(player, new SyncToastsPayload(ids));
    }

    public static void applyAssignedQuest(ServerPlayer player, Quest quest) {
        PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
        Quest assignedQuest = QuestManager.getQuestById(quest.getAssignedQuestId());
        if(data == null || assignedQuest == null) return;

        data.putState(assignedQuest.getId(), QuestState.IN_PROGRESS);
        QuestManager.sendToast(player, assignedQuest);
    }
}
