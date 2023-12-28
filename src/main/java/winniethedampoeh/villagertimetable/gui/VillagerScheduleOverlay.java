package winniethedampoeh.villagertimetable.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.util.Identifier;
import winniethedampoeh.villagertimetable.Timetable;
import winniethedampoeh.villagertimetable.VillagerTimetable;
import winniethedampoeh.villagertimetable.config.VillagerTimetableConfig;

public class VillagerScheduleOverlay implements HudRenderCallback {
    private static final Identifier SLEEPING = new Identifier(VillagerTimetable.MODID,
            "textures/hud/sleeping.png");
    private static final Identifier IDLE = new Identifier(VillagerTimetable.MODID,
            "textures/hud/idle.png");
    private static final Identifier WORKING = new Identifier(VillagerTimetable.MODID,
            "textures/hud/working.png");
    private static final Identifier MEET = new Identifier(VillagerTimetable.MODID,
            "textures/hud/meet.png");
    private static final Identifier PLAYING = new Identifier(VillagerTimetable.MODID,
            "textures/hud/playing.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if(!VillagerTimetableConfig.INSTANCE.guiRendering.get()) return;

        Identifier icon = getScheduleIcon();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, icon);

        int[] dims = getDrawDimensions();

        drawContext.drawTexture(icon, dims[0], dims[1], 0, 0, dims[2], dims[3], dims[2], dims[3]);
    }

    private Identifier getScheduleIcon() {
        Activity activity = Timetable.getCurrentActivity(Timetable.VillagerType.EMPLOYED);
        if(activity.equals(Activity.REST)) {
            return SLEEPING;
        } else if (activity.equals(Activity.IDLE)) {
            return IDLE;
        } else if (activity.equals(Activity.WORK)) {
            return WORKING;
        } else if (activity.equals(Activity.MEET)) {
            return MEET;
        }

        return IDLE;
    }

    private int[] getDrawDimensions() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return new int[] {0, 0, 0, 0};
        }

        int[] dimensions = new int[4];
        switch(VillagerTimetableConfig.INSTANCE.renderLocation.get()){
            case HOTBAR_LEFT -> {
                int x = client.getWindow().getScaledWidth() / 2;
                int y = client.getWindow().getScaledHeight();
                dimensions[0] = x - 91 - 18 - 2;
                dimensions[1] = y - 18 - 2;
                dimensions[2] = 18;
                dimensions[3] = 18;
                return dimensions;
            }
            case HOTBAR_RIGHT -> {
                int x = client.getWindow().getScaledWidth() / 2;
                int y = client.getWindow().getScaledHeight();
                dimensions[0] = x + 91 + 2;
                dimensions[1] = y - 18 - 2;
                dimensions[2] = 18;
                dimensions[3] = 18;
                return dimensions;
            }
            case NORTH_EAST -> {
                dimensions[0] = client.getWindow().getScaledWidth() - 22 - 2;
                dimensions[1] = 2;
                dimensions[2] = 22;
                dimensions[3] = 22;
                return dimensions;
            }
            case NORTH_WEST -> {
                dimensions[0] = 2;
                dimensions[1] = 2;
                dimensions[2] = 22;
                dimensions[3] = 22;
                return dimensions;
            }
            case SOUTH_EAST -> {
                dimensions[0] = client.getWindow().getScaledWidth() - 22 - 2;
                dimensions[1] = client.getWindow().getScaledHeight() - 22 - 2;
                dimensions[2] = 22;
                dimensions[3] = 22;
                return dimensions;
            }
            case SOUTH_WEST -> {
                dimensions[0] = 2;
                dimensions[1] = client.getWindow().getScaledHeight() - 22 - 2;
                dimensions[2] = 22;
                dimensions[3] = 22;
                return dimensions;
            }
        }
        return new int[] {0, 0, 0, 0};
    }
}
