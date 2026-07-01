package ru.cityheroes.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import ru.cityheroes.CityHeroesMod;

import java.util.ArrayList;
import java.util.List;

//TODO ПЕРЕПИСАТЬ НННАХУЙ ВООБЩЕ ВСЁ ЧТО СВЯЗАНО С ТОСТАМИ
public record SyncToastsPayload(List<String> questIds) implements CustomPacketPayload {
    public static final Type<SyncToastsPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    CityHeroesMod.MOD_ID,
                    "sync_toasts"
            ));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncToastsPayload> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public SyncToastsPayload decode(RegistryFriendlyByteBuf buf) {
                    int size = buf.readVarInt();
                    List<String> ids = new ArrayList<>(size);

                    for (int i = 0; i < size; i++) {
                        ids.add(buf.readUtf());
                    }

                    return new SyncToastsPayload(ids);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, SyncToastsPayload payload) {
                    buf.writeVarInt(payload.questIds().size());

                    for (String id : payload.questIds()) {
                        buf.writeUtf(id);
                    }
                }
            };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}