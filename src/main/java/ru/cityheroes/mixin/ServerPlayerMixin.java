package ru.cityheroes.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.cityheroes.quests.QuestManager;
import ru.cityheroes.quests.event.QuestEvent;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Unique
    private Vec3 cityheroes$lastPos;

    @Inject(method = "tick", at = @At("TAIL"))
    private void cityheroes$fireMoveEvent(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        Vec3 current = player.position();

        if (cityheroes$lastPos == null) {
            cityheroes$lastPos = current;
            return;
        }

        if (!current.equals(cityheroes$lastPos)) {
            QuestManager.fire(new QuestEvent.MoveEvent(
                    player,
                    cityheroes$lastPos,
                    current
            ));

            cityheroes$lastPos = current;
        }
    }
}
