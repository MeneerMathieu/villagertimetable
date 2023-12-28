package winniethedampoeh.villagertimetable.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class TableWidget extends ClickableWidget {
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

    public TableWidget(float scale, Identifier texture, int texureWidth, int textureHeight, int regionWidth, int regionHeight, int x, int y, int width, int height, int u, int v, Text message) {
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
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1.0f);
        context.drawTexture(texture, texture_x, texture_y, regionWidth, regionHeight, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
        context.getMatrices().pop();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
