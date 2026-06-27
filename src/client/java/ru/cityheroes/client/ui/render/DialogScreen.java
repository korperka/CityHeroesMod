package ru.cityheroes.client.ui.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.w3c.dom.Text;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.client.enums.Textures;
import ru.cityheroes.client.ui.widgets.TypewriterWidget;

public class DialogScreen extends Screen {
    private TypewriterWidget text;
    private static final int DIALOG_WIDTH = 360;
    private static final int DIALOG_HEIGHT = 250;
    private static final int DIALOG_TEXTBOX_WIDTH = 115;
    private static final int DIALOG_TEXTBOX_HEIGHT = 60;
    private int left;
    private int top;

    public DialogScreen(String dialogId) {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();

        left = (width - DIALOG_WIDTH) / 2;
        top = (height - DIALOG_HEIGHT) / 2;

        text = new TypewriterWidget(left + DIALOG_WIDTH / 3, top + DIALOG_HEIGHT / 6, DIALOG_TEXTBOX_WIDTH, DIALOG_TEXTBOX_HEIGHT, Component.literal("а".repeat(1000)));
        addRenderableWidget(text);
    }

    @Override
    public void tick() {
        super.tick();

        text.tick();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Textures.DIALOG_TEXTURE,
                left,
                top,
                0f,
                0f,
                DIALOG_WIDTH,
                DIALOG_HEIGHT,
                DIALOG_WIDTH,
                DIALOG_HEIGHT
        );

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }
}
