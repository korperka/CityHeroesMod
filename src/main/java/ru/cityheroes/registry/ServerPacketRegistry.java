package ru.cityheroes.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import ru.cityheroes.data.ModAttachments;
import ru.cityheroes.data.PlayerQuestData;
import ru.cityheroes.dialogs.Dialog;
import ru.cityheroes.dialogs.DialogManager;
import ru.cityheroes.packet.DialogFinishedPayload;
import ru.cityheroes.packet.SyncToastsPayload;
import ru.cityheroes.packet.OpenDialogPayload;
import ru.cityheroes.packet.ShowToastPayload;
import ru.cityheroes.quests.Quest;
import ru.cityheroes.quests.QuestManager;
import ru.cityheroes.quests.QuestState;

public class ServerPacketRegistry {
    public static void registerAll() {
        PayloadTypeRegistry.clientboundPlay().register(OpenDialogPayload.TYPE, OpenDialogPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ShowToastPayload.TYPE, ShowToastPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncToastsPayload.TYPE, SyncToastsPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(DialogFinishedPayload.TYPE, DialogFinishedPayload.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                DialogFinishedPayload.TYPE,
                (payload, context) -> {
                    ServerPlayer player = context.player();
                    PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
                    Dialog dialog = DialogManager.getDialog(payload.dialogId());
                    String questId = QuestManager.getQuestByDialog(dialog.getId()).getId();
                    Quest quest = QuestManager.getQuestById(questId);

                    if(data == null) {
                        data = new PlayerQuestData();
                        player.setAttached(ModAttachments.QUEST_DATA, data);
                    }

                    if(data.getState(questId) == QuestState.NOT_STARTED) {
                        data.putState(questId, QuestState.IN_PROGRESS);
                        if(!quest.isShowToast()) return;

                        QuestManager.sendToast(player, quest);
                    }
                }
        );
    }
}
