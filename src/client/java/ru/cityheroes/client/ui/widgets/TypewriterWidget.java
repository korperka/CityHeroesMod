package ru.cityheroes.client.ui.widgets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import org.jspecify.annotations.NonNull;
import ru.cityheroes.data.ModAttachments;
import ru.cityheroes.data.PlayerQuestData;
import ru.cityheroes.packet.DialogFinishedPayload;

import java.util.List;

public class TypewriterWidget extends MultiLineTextWidget {
    private final List<Component> phrases;
    private final String dialogId;
    private int currentPhraseIndex;
    private int visibleCharacters;
    private final String playerName;

    public TypewriterWidget(int x, int y, int width, int height, String dialogId, List<Component> phrases, String playerName) {
        super(x, y, phrases.getFirst(), Minecraft.getInstance().font);

        setMaxWidth(width);
        setMaxRows(height / getFont().lineHeight);
        this.phrases = phrases;
        this.visibleCharacters = 0;
        this.currentPhraseIndex = 0;
        this.dialogId = dialogId;
        this.playerName = playerName;
    }

    public void completePhrase() {
        visibleCharacters = getCurrentText().length();
    }

    public void nextPhrase() {
        if(!isPhraseFullyVisible()) {
            completePhrase();
            return;
        }
        if(isLastPhrase()) {
            ClientPlayNetworking.send(new DialogFinishedPayload(dialogId));
            Minecraft.getInstance().gui.setScreen(null);
            return;
        }

        currentPhraseIndex++;
        visibleCharacters = 0;
    }

    public void previousPhrase() {
        if(currentPhraseIndex <= 0) return;

        currentPhraseIndex--;
        visibleCharacters = 0;
    }

    @Override
    public @NonNull Component getMessage() {
        return Component.literal(getCurrentText().substring(0, visibleCharacters)).withoutShadow().withColor(TextColor.BLACK);
    }

    //TODO WARNING GOVNOKOD REMOVE THIS SHIT LATER PLEASE
    private String getCurrentText() {
        return getCurrentPhrase().getString().replace("{name}", playerName);
    }

    public void tick() {
        if(visibleCharacters < getCurrentText().length()) {
            visibleCharacters++;

            Minecraft mc = Minecraft.getInstance();
            if (visibleCharacters % 2 != 0) return;
            if (mc.player == null) return;

            mc.player.playSound(
                    SoundEvents.UI_BUTTON_CLICK.value(),
                    0.15F,
                    1.8F + mc.level.getRandom().nextFloat() * 0.2F
            );
        }
    }

    public boolean isLastPhrase() {
        return currentPhraseIndex == phrases.size() - 1;
    }

    public boolean isPhraseFullyVisible() {
        return visibleCharacters >= getCurrentText().length();
    }

    private Component getCurrentPhrase() {
        return phrases.get(currentPhraseIndex);
    }
}
