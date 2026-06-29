package ru.cityheroes.client.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class QuestToast implements Toast {

    private static final Identifier BACKGROUND =
            Identifier.withDefaultNamespace("toast/system");

    private static final int MAX_LINE_SIZE = 200;
    private static final int LINE_HEIGHT = 12;
    private static final int MARGIN = 10;
    private static final int TEXT_X = 18;

    private final Object token = new Object();

    private List<FormattedCharSequence> titleLines;
    private List<FormattedCharSequence> messageLines;

    private boolean forceHide;
    private Toast.Visibility wantedVisibility = Visibility.HIDE;

    private int width;

    public QuestToast(Component title, Component message) {
        this.titleLines = split(title);
        this.messageLines = message == null ? List.of() : split(message);
        recalcWidth();
    }

    private static List<FormattedCharSequence> split(Component text) {
        return Minecraft.getInstance().font.split(text, MAX_LINE_SIZE);
    }

    // ===================== SHOW / HIDE =====================

    public static QuestToast show(ToastManager manager, Component title, Component message) {
        QuestToast toast = new QuestToast(title, message);
        manager.addToast(toast);
        toast.wantedVisibility = Visibility.SHOW;
        return toast;
    }

    public void hide() {
        this.forceHide = true;
        this.wantedVisibility = Visibility.HIDE;
    }

    @Override
    public Toast.Visibility getWantedVisibility() {
        return forceHide ? Visibility.HIDE : wantedVisibility;
    }

    @Override
    public void update(ToastManager manager, long fullyVisibleForMs) {
        if (forceHide) {
            wantedVisibility = Visibility.HIDE;
        }
    }

    // ===================== RENDER =====================

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics,
                                   Font font,
                                   long timeVisible) {

        int height = height();

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                BACKGROUND,
                0,
                0,
                width,
                height
        );

        if (messageLines.isEmpty()) {
            drawLines(graphics, font, titleLines, 12, 0xFFFFFFFF);
        } else {
            drawLines(graphics, font, titleLines, 7, 0xFFFFFFFF);
            drawLines(graphics, font, messageLines,
                    7 + titleLines.size() * LINE_HEIGHT,
                    0xFFCCCCCC);
        }
    }

    private void drawLines(GuiGraphicsExtractor graphics,
                           Font font,
                           List<FormattedCharSequence> lines,
                           int yStart,
                           int color) {

        for (int i = 0; i < lines.size(); i++) {
            graphics.text(
                    font,
                    lines.get(i),
                    TEXT_X,
                    yStart + i * LINE_HEIGHT,
                    color,
                    false
            );
        }
    }

    // ===================== SIZE =====================

    public int width() {
        return width;
    }

    public int height() {
        int titleH = (titleLines.size() - 1) * LINE_HEIGHT;
        int msgH = Math.max(messageLines.size(), 1) * LINE_HEIGHT;
        return 20 + titleH + msgH;
    }

    private void recalcWidth() {
        Font font = Minecraft.getInstance().font;

        int max = 160;

        for (FormattedCharSequence line : titleLines) {
            max = Math.max(max, font.width(line));
        }

        for (FormattedCharSequence line : messageLines) {
            max = Math.max(max, font.width(line));
        }

        this.width = max + 30;
    }

    @Override
    public Object getToken() {
        return token;
    }
}