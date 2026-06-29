package ru.cityheroes.client.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import ru.cityheroes.client.ui.hud.QuestToast;
import ru.cityheroes.client.ui.render.DialogScreen;
import ru.cityheroes.packet.OpenDialogPayload;
import ru.cityheroes.packet.ShowToastPayload;

public class ClientPacketRegistry {
    public static void registerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(
                OpenDialogPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        context.client().gui.setScreen(
                                new DialogScreen(payload.dialogId(), payload.entityId())
                        );
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                ShowToastPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        QuestToast.show(context.client().gui.toastManager(), Component.literal(payload.title()), Component.literal(payload.message()));
                    });
                }
        );
    }
}
