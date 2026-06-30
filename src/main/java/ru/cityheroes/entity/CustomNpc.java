package ru.cityheroes.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import ru.cityheroes.data.ModAttachments;
import ru.cityheroes.data.PlayerQuestData;
import ru.cityheroes.dialogs.Dialog;
import ru.cityheroes.npc.NpcDto;
import ru.cityheroes.npc.NpcManager;
import ru.cityheroes.packet.HideToastPayload;
import ru.cityheroes.packet.OpenDialogPayload;
import ru.cityheroes.quests.Quest;
import ru.cityheroes.quests.QuestManager;
import ru.cityheroes.quests.QuestState;
import ru.cityheroes.quests.objective.interfaces.ImmediateQuestObjective;

import java.util.List;

public class CustomNpc extends PathfinderMob {
    private static final EntityDataAccessor<String> NPC_ID =
            SynchedEntityData.defineId(CustomNpc.class, EntityDataSerializers.STRING);

    public CustomNpc(EntityType<? extends PathfinderMob> entityType, Level level) {
        this(entityType, level, "sample");
    }

    public CustomNpc(EntityType<? extends PathfinderMob> entityType, Level level, String npcId) {
        super(entityType, level);

        this.setNoAi(true);
        this.setInvulnerable(true);
        this.setSilent(true);

        setNpcId(npcId);
    }

    @Override
    public @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.SUCCESS;
        }

        PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
        if (data == null) {
            data = new PlayerQuestData();
            player.setAttached(ModAttachments.QUEST_DATA, data);
        }

        List<String> npcQuests = NpcManager.getQuestsByNpc(getNpcId());
        if (npcQuests == null || npcQuests.isEmpty()) {
            return InteractionResult.SUCCESS;
        }

        String nextQuestId = null;

        for (String questId : npcQuests) {
            Quest quest = QuestManager.getQuestById(questId);
            QuestState state = data.getState(questId);

            switch (state) {
                case IN_PROGRESS -> {
                    if (quest.checkCompleted(player)) {
                        data.putState(questId, QuestState.COMPLETED);
                        ServerPlayNetworking.send(serverPlayer, new HideToastPayload(questId));
                    }

                    openDialog(serverPlayer, quest);
                    return InteractionResult.SUCCESS;
                }

                case COMPLETED -> {
                    openDialog(serverPlayer, quest);
                    data.putState(questId, QuestState.CHECKED);
                    return InteractionResult.SUCCESS;
                }

                case NOT_STARTED -> {
                    if (nextQuestId == null) {
                        nextQuestId = questId;
                    }
                }

                case CHECKED -> { }
            }
        }

        if (nextQuestId != null) {
            openDialog(serverPlayer, QuestManager.getQuestById(nextQuestId));
        } else {
            serverPlayer.sendSystemMessage(
                    Component.literal("Кажется, ему больше нечего тебе сказать...")
                            .withColor(TextColor.GRAY)
                            .withStyle(ChatFormatting.ITALIC)
            );
        }

        return InteractionResult.SUCCESS;
    }

    private void openDialog(ServerPlayer player, Quest quest) {
        Dialog dialog = quest.getCurrentDialog(player);
        PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
        if(data == null) return;

        String questId = QuestManager.getQuestByDialog(dialog.getId()).getId();
        boolean showHint = data.getState(questId) == QuestState.NOT_STARTED;

        ServerPlayNetworking.send(
                player,
                new OpenDialogPayload(dialog.getId(), getId(), showHint)
        );
    }

    public static AttributeSupplier.Builder createCubeAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 1);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(NPC_ID, "");
    }

    public void setNpcId(String id) {
        entityData.set(NPC_ID, id);
    }

    public String getNpcId() {
        return entityData.get(NPC_ID);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("npcId", getNpcId());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        if (input.contains("npcId")) {
            setNpcId(input.getString("npcId").orElse(""));
        }
    }

    @Override
    public @NonNull Component getDisplayName() {
        NpcDto npcDto = NpcManager.getNpc(getNpcId());
        return npcDto == null ? Component.empty() : Component.literal(npcDto.getDisplayName());
    }
}
