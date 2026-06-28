package ru.cityheroes.client.ui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class TypewriterWidget extends MultiLineTextWidget {
    private final List<Component> phrases;
    private int currentPhraseIndex;
    private int visibleCharacters;

    public TypewriterWidget(int x, int y, int width, int height, List<Component> phrases) {
        super(x, y, phrases.getFirst(), Minecraft.getInstance().font);

        setMaxWidth(width);
        setMaxRows(height / getFont().lineHeight);
        this.phrases = phrases;
        this.visibleCharacters = 0;
        this.currentPhraseIndex = 0;
    }

    public void completePhrase() {
        visibleCharacters = getCurrentPhrase().getString().length();
    }

    public void nextPhrase() {
        if(visibleCharacters < getCurrentPhrase().getString().length()) {
            completePhrase();
            return;
        }
        if(currentPhraseIndex >= phrases.size() - 1) return;

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
        return Component.literal(getCurrentPhrase().getString().substring(0, visibleCharacters)).withoutShadow().withColor(TextColor.BLACK);
    }

    public void tick() {
        if(visibleCharacters < getCurrentPhrase().getString().length()) {
            visibleCharacters++;
        }
    }

    private Component getCurrentPhrase() {
        return phrases.get(currentPhraseIndex);
    }
}
