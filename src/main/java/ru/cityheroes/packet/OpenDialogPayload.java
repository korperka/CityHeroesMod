package ru.cityheroes.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import ru.cityheroes.CityHeroesMod;

public record OpenDialogPayload(String dialogId, int entityId, boolean showHint) implements CustomPacketPayload {
    public static final Type<OpenDialogPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    CityHeroesMod.MOD_ID,
                    "open_dialog"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenDialogPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    OpenDialogPayload::dialogId,
                    ByteBufCodecs.INT,
                    OpenDialogPayload::entityId,
                    ByteBufCodecs.BOOL,
                    OpenDialogPayload::showHint,

                    OpenDialogPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
