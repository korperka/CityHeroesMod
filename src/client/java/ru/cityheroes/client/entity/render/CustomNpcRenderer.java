package ru.cityheroes.client.entity.render;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.entity.CustomNpc;
import ru.cityheroes.npc.NpcDto;
import ru.cityheroes.npc.NpcManager;

public class CustomNpcRenderer extends LivingEntityRenderer<CustomNpc, AvatarRenderState, PlayerModel> {
    public CustomNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
    }

    @Override
    public void extractRenderState(CustomNpc entity, AvatarRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        NpcDto npcDto = NpcManager.getNpc(entity.getNpcId());
        state.skin = PlayerSkin.insecure(
                new ClientAsset.ResourceTexture(
                npcDto == null ? Identifier.withDefaultNamespace("textures/entity/steve.png") : Identifier.parse(npcDto.getSkinPath())),
                null,
                null,
                npcDto == null ? PlayerModelType.WIDE : npcDto.getModelType()
        );
    }

    @Override
    public AvatarRenderState createRenderState() {
        return new AvatarRenderState();
    }

    @Override
    public Identifier getTextureLocation(AvatarRenderState state) {
        //она не используется лол
        return null;
    }
}
