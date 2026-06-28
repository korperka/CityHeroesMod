package ru.cityheroes.client.ui.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.w3c.dom.Text;
import ru.cityheroes.CityHeroesMod;
import ru.cityheroes.client.entity.render.CustomNpcRenderer;
import ru.cityheroes.client.enums.Textures;
import ru.cityheroes.client.ui.widgets.TypewriterWidget;

public class DialogScreen extends Screen {
    private TypewriterWidget text;
    private int left;
    private int top;
    private final int entityId;

    private static final int DIALOG_WIDTH = 360; //in-game dialog texture sizes
    private static final int DIALOG_HEIGHT = 250;
    private static final int TEXTURE_WIDTH = 180; //original dialog texture sizes
    private static final int TEXTURE_HEIGHT = 125;
    private static final int TEXTBOX_ORIGINAL_WIDTH = 110; //original sizes of textbox
    private static final int TEXTBOX_ORIGINAL_HEIGHT = 50;
    private static final int TEXTBOX_ORIGINAL_X = 60;
    private static final int TEXTBOX_ORIGINAL_Y = 20;

    private static final float SCALE_X = (float) DIALOG_WIDTH / TEXTURE_WIDTH;
    private static final float SCALE_Y = (float) DIALOG_HEIGHT / TEXTURE_HEIGHT;

    private static final int DIALOG_TEXTBOX_WIDTH =
            Math.round(TEXTBOX_ORIGINAL_WIDTH * SCALE_X);
    private static final int DIALOG_TEXTBOX_HEIGHT =
            Math.round(TEXTBOX_ORIGINAL_HEIGHT * SCALE_Y);
    private static final int DIALOG_TEXTBOX_X =
            Math.round(TEXTBOX_ORIGINAL_X * SCALE_X);
    private static final int DIALOG_TEXTBOX_Y =
            Math.round(TEXTBOX_ORIGINAL_Y * SCALE_Y);


    public DialogScreen(String dialogId, int entityId) {
        super(Component.empty());

        this.entityId = entityId;
    }

    @Override
    protected void init() {
        super.init();

        left = (width - DIALOG_WIDTH) / 2;
        top = (height - DIALOG_HEIGHT) / 2;

        text = new TypewriterWidget(left + DIALOG_TEXTBOX_X, top + DIALOG_TEXTBOX_Y, DIALOG_TEXTBOX_WIDTH, DIALOG_TEXTBOX_HEIGHT, Component.literal("а".repeat(1000)));
        addRenderableWidget(text);
    }

    @Override
    public void tick() {
        super.tick();

        text.tick();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        Minecraft minecraft = Minecraft.getInstance();

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

        int x = left + 26;
        int y = top + 28;
        int width = 86;
        int height = 150;

        graphics.entity(
                minecraft.getEntityRenderDispatcher()
                        .extractEntity(minecraft.level.getEntity(entityId), 1.0F),
                45,
                new Vector3f(0, 1, 0),
                new Quaternionf()
                        .rotateY((float) Math.PI)
                        .rotateZ((float) Math.PI),
                null,
                x,
                y,
                x + width,
                y + height
        );

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }
}
