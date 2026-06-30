package ru.cityheroes.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import ru.cityheroes.entity.CustomNpc;
import ru.cityheroes.npc.NpcManager;
import ru.cityheroes.registry.ModEntityTypes;

public final class CityHeroesCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("ch")
                        .requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.GAMEMASTERS)))
                        .then(
                                Commands.literal("spawn")
                                        .then(
                                                Commands.argument("id", StringArgumentType.word())
                                                        .suggests((context, builder) -> {
                                                            NpcManager.getNpcs().keySet()
                                                                    .forEach(builder::suggest);

                                                            return builder.buildFuture();
                                                        })
                                                        .executes(CityHeroesCommands::spawnNpc)
                                        )
                        )
        );
    }

    private static int spawnNpc(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String npcId = StringArgumentType.getString(context, "id");

        ServerLevel level = player.level();
        BlockPos pos = player.blockPosition();

        CustomNpc npc = ModEntityTypes.CUSTOM_NPC.create(level, EntitySpawnReason.COMMAND);

        if (npc == null) {
            context.getSource().sendFailure(Component.literal("Не удалось заспавнить нпс"));
            return 0;
        }

        npc.setNpcId(npcId);
        npc.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        npc.finalizeSpawn(
                level,
                level.getCurrentDifficultyAt(pos),
                EntitySpawnReason.COMMAND,
                null
        );

        level.addFreshEntity(npc);

        context.getSource().sendSuccess(
                () -> Component.literal("Заспавнен '" + npcId + "'."),
                true
        );

        return Command.SINGLE_SUCCESS;
    }

    private CityHeroesCommands() {}
}