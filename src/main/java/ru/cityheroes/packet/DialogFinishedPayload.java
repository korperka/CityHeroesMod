package ru.cityheroes.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import ru.cityheroes.CityHeroesMod;

public record DialogFinishedPayload(String dialogId) implements CustomPacketPayload {
    public static final Type<DialogFinishedPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    CityHeroesMod.MOD_ID,
                    "dialog_finished"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DialogFinishedPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    DialogFinishedPayload::dialogId,

                    DialogFinishedPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
