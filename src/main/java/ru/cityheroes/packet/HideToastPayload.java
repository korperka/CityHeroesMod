package ru.cityheroes.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import ru.cityheroes.CityHeroesMod;

public record HideToastPayload(String questId) implements CustomPacketPayload {
    public static final Type<HideToastPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(CityHeroesMod.MOD_ID, "update_toasts"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HideToastPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    HideToastPayload::questId,

                    HideToastPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
