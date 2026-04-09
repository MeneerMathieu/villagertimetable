package winniethedampoeh.villagertimetable.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TableWidget extends AbstractWidget {
    private final Identifier texture;

    private final int texture_x;
    private final int texture_y;

    private final int u;
    private final int v;
    private final int textureWidth;
    private final int textureHeight;
    private final int regionWidth;
    private final int regionHeight;
    private final float scale;

    public TableWidget(float scale, Identifier texture, int texureWidth, int textureHeight, int regionWidth, int regionHeight, int x, int y, int width, int height, int u, int v, Component message) {
        super((int) (x * scale), (int) (y * scale), (int) (width * scale), (int) (height * scale), message);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.textureWidth = texureWidth;
        this.textureHeight = textureHeight;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.texture_x = x;
        this.texture_y = y;
        this.scale = scale;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.pose().pushMatrix();
        graphics.pose().scale(scale, scale);
        graphics.blit(RenderPipelines.GUI_TEXTURED,
                texture,
                texture_x,
                texture_y,
                u,
                v,
                regionWidth,
                regionHeight,
                regionWidth,
                regionHeight,
                textureWidth,
                textureHeight);
        graphics.pose().popMatrix();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {

    }
}
