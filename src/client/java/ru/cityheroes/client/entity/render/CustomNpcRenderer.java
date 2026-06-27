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

public class CustomNpcRenderer extends LivingEntityRenderer<CustomNpc, AvatarRenderState, PlayerModel> {
    //TODO
    private static final PlayerSkin NPC_SKIN =
            PlayerSkin.insecure(
                    new ClientAsset.ResourceTexture(
                            Identifier.fromNamespaceAndPath(
                                    CityHeroesMod.MOD_ID,
                                    "entity/npc"
                            )
                    ),
                    null,
                    null,
                    PlayerModelType.WIDE
            );

    public CustomNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
    }

    @Override
    public void extractRenderState(CustomNpc entity, AvatarRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.skin = NPC_SKIN;
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
