package ru.cityheroes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cityheroes.command.CityHeroesCommands;
import ru.cityheroes.dialogs.DialogManager;
import ru.cityheroes.npc.NpcManager;
import ru.cityheroes.quests.QuestManager;
import ru.cityheroes.registry.ModEntityTypes;
import ru.cityheroes.registry.ServerPacketRegistry;

public class CityHeroesMod implements ModInitializer {
	public static final String MOD_ID = "cityheroes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerPacketRegistry.registerAll();
		ModEntityTypes.registerAttributes();

		DialogManager.loadDialogs();
		QuestManager.loadQuests();
		NpcManager.loadNpcs();

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) ->
						CityHeroesCommands.register(dispatcher)
		);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
