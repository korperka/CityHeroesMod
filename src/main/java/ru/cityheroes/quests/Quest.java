package ru.cityheroes.quests;

import com.fasterxml.jackson.annotation.JsonIgnore;import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.world.entity.player.Player;
import ru.cityheroes.data.ModAttachments;
import ru.cityheroes.data.PlayerQuestData;
import ru.cityheroes.dialogs.Dialog;
import ru.cityheroes.dialogs.DialogManager;
import ru.cityheroes.quests.objective.interfaces.QuestObjective;
import ru.cityheroes.quests.objective.interfaces.TalkQuestObjective;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Quest {
    private String id;
    private String name;
    private QuestObjective objective;
    @JsonIgnore
    private QuestState state;
    private Map<QuestState, String> dialogs;
    private boolean showToast;
    private String assignedQuestId = "";

    public boolean checkCompleted(Player player) {
        return objective instanceof TalkQuestObjective talkObjective && talkObjective.checkCompleted(player);
    }

    public Dialog getCurrentDialog(Player player) {
        PlayerQuestData data = player.getAttached(ModAttachments.QUEST_DATA);
        return data == null ? null : DialogManager.getDialog(dialogs.get(data.getState(id)));
    }
}
