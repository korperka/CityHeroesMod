package ru.cityheroes.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import ru.cityheroes.CityHeroesMod;

public record ShowToastPayload(String title, String message) implements CustomPacketPayload {
    public static final Type<ShowToastPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    CityHeroesMod.MOD_ID,
                    "show_toast"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ShowToastPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    ShowToastPayload::title,
                    ByteBufCodecs.STRING_UTF8,
                    ShowToastPayload::message,

                    ShowToastPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
