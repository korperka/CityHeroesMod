package ru.cityheroes.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import ru.cityheroes.packet.OpenDialogPayload;

public class ServerPacketRegistry {
    public static void registerAll() {
        PayloadTypeRegistry.clientboundPlay().register(
                OpenDialogPayload.TYPE,
                OpenDialogPayload.STREAM_CODEC
        );
    }
}
