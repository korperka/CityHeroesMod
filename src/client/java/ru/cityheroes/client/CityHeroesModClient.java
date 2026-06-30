package ru.cityheroes.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import ru.cityheroes.client.entity.render.CustomNpcRenderer;
import ru.cityheroes.client.registry.ClientPacketRegistry;
import ru.cityheroes.registry.ModEntityTypes;

public class CityHeroesModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPacketRegistry.registerPackets();
		EntityRenderers.register(
				ModEntityTypes.CUSTOM_NPC,
				CustomNpcRenderer::new
		);
	}
}