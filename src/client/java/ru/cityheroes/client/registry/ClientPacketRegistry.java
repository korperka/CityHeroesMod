package ru.cityheroes.client.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import ru.cityheroes.client.ui.hud.QuestToast;
import ru.cityheroes.client.ui.render.DialogScreen;
import ru.cityheroes.packet.SyncToastsPayload;
import ru.cityheroes.packet.OpenDialogPayload;
import ru.cityheroes.packet.ShowToastPayload;
import ru.cityheroes.quests.Quest;
import ru.cityheroes.quests.QuestManager;

public class ClientPacketRegistry {
    public static void registerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(
                OpenDialogPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        context.client().gui.setScreen(
                                new DialogScreen(payload.dialogId(), payload.entityId(), payload.showHint(), payload.playerName())
                        );
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                ShowToastPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        QuestToast.show(context.client().gui.toastManager(), payload.questId(), Component.literal(payload.title()), Component.literal(payload.message()));
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                SyncToastsPayload.TYPE,
                (payload, context) -> context.client().execute(() -> {
                    var manager = context.client().gui.toastManager();
                    manager.clear();

                    for (String id : payload.questIds()) {
                        Quest quest = QuestManager.getQuestById(id);

                        if (quest != null) {
                            QuestToast.show(
                                    manager,
                                    id,
                                    Component.literal("Активное задание:"),
                                    Component.literal(quest.getName())
                            );
                        }
                    }
                })
        );
    }
}
