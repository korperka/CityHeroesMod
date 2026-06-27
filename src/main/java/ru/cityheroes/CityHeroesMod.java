package ru.cityheroes;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cityheroes.packet.OpenDialogPayload;
import ru.cityheroes.registry.ModEntityTypes;
import ru.cityheroes.registry.ServerPacketRegistry;

public class CityHeroesMod implements ModInitializer {
	public static final String MOD_ID = "cityheroes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerPacketRegistry.registerAll();
		ModEntityTypes.registerAttributes();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
