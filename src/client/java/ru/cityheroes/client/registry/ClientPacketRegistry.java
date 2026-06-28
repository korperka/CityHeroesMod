package ru.cityheroes.client.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import ru.cityheroes.client.ui.render.DialogScreen;
import ru.cityheroes.packet.OpenDialogPayload;

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
    }
}
