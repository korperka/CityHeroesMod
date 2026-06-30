package ru.cityheroes.client.ui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import ru.cityheroes.client.enums.Textures;
import ru.cityheroes.client.ui.widgets.TypewriterWidget;
import ru.cityheroes.dialogs.DialogManager;
import ru.cityheroes.quests.QuestManager;

import static ru.cityheroes.client.ui.render.DialogLayout.*;

public class DialogScreen extends Screen {
    private TypewriterWidget text;
    private int left;
    private int top;
    private final int entityId;
    private final String dialogId;
    private final boolean showHint;
    private final String playerName;

    public DialogScreen(String dialogId, int entityId, boolean showHint, String playerName) {
        super(Component.empty());

        this.dialogId = dialogId;
        this.entityId = entityId;
        this.showHint = showHint;
        this.playerName = playerName;
    }

    @Override
    protected void init() {
        super.init();

        left = (width - DIALOG_WIDTH) / 2;
        top = (height - DIALOG_HEIGHT) / 2;

        text = new TypewriterWidget(left + TEXTBOX_X, top + TEXTBOX_Y, TEXTBOX_WIDTH, TEXTBOX_HEIGHT,
                dialogId,
                DialogManager.getDialogs().get(dialogId).getPhrases().stream().map(s -> (Component) Component.literal(s)).toList(),
                playerName
        );
        addRenderableWidget(text);
    }

    @Override
    public void tick() {
        super.tick();
        text.tick();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        Minecraft minecraft = Minecraft.getInstance();

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.DIALOG_TEXTURE,
                left,
                top,
                0,
                0,
                DIALOG_WIDTH,
                DIALOG_HEIGHT,
                DIALOG_WIDTH,
                DIALOG_HEIGHT
        );

        int x = left + AVATAR_X;
        int y = top + AVATAR_Y;
        graphics.entity(
                minecraft.getEntityRenderDispatcher()
                        .extractEntity(minecraft.level.getEntity(entityId), 1),
                45,
                new Vector3f(0, 1.22f, 0),
                new Quaternionf()
                        .rotateZ((float) Math.PI),
                null,
                x,
                y,
                x + AVATAR_WIDTH,
                y + AVATAR_HEIGHT
        );

        if (text.isLastPhrase() && text.isPhraseFullyVisible() && showHint) {
            drawAcceptHint(graphics);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    private void drawAcceptHint(GuiGraphicsExtractor graphics) {
        Minecraft mc = Minecraft.getInstance();

        Component hint = Component.literal("[ЛКМ] - принять задание");

        float t = (System.currentTimeMillis() % 1000L) / 1000f;
        float alpha = 0.85f + 0.15f * (float)Math.sin(t * Math.PI * 2);

        int color = ((int) (alpha * 255) << 24) | 0xFFFFFF;

        int x = left + DIALOG_WIDTH - mc.font.width(hint) - 10;
        int y = top + DIALOG_HEIGHT - mc.font.lineHeight - 8;

        graphics.text(
                mc.font,
                hint,
                x,
                y,
                color,
                true
        );
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if(event.button() == 0) {
            text.nextPhrase();
        }
        else if(event.button() == 1) {
            text.previousPhrase();
        }
        else return super.mouseClicked(event, doubleClick);

        return true;
    }
}
