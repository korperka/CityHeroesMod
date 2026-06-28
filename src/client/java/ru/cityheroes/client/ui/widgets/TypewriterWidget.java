package ru.cityheroes.client.ui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import org.jspecify.annotations.NonNull;

public class TypewriterWidget extends MultiLineTextWidget {
    private final Component fullText;
    private int visibleCharacters;

    public TypewriterWidget(int x, int y, int width, int height, Component fullText) {
        super(x, y, fullText, Minecraft.getInstance().font);

        setMaxWidth(width);
        setMaxRows(height / getFont().lineHeight);
        this.fullText = fullText;
        this.visibleCharacters = 0;
    }

    @Override
    public @NonNull Component getMessage() {
        return Component.literal(fullText.getString().substring(0, visibleCharacters)).withColor(TextColor.BLACK).withoutShadow().copy();
    }

    public void tick() {
        if(visibleCharacters < fullText.getString().length()) {
            visibleCharacters++;
        }
    }
}
