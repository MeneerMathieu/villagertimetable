package winniethedampoeh.villagertimetable.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import winniethedampoeh.villagertimetable.VillagerTimetable;
import winniethedampoeh.villagertimetable.config.Option;
import winniethedampoeh.villagertimetable.config.VillagerTimetableConfig;
import winniethedampoeh.villagertimetable.control.KeyBinds;

public class VillagerTimetableScreen extends Screen {
    public static final Identifier SCREEN_TEXTURE = Identifier.tryBuild(VillagerTimetable.MODID,
            "textures/screen/timetable.png");
    public static final Identifier BABY_VILLAGER = Identifier.tryBuild(VillagerTimetable.MODID,
            "textures/screen/baby_villager.png");
    public static final Identifier NITWIT = Identifier.tryBuild(VillagerTimetable.MODID,
            "textures/screen/nitwit.png");
    public static final Identifier FARMER = Identifier.tryBuild(VillagerTimetable.MODID,
            "textures/screen/farmer.png");

    private int x;
    private int y;
    private int width;
    private int height;

    private final float scale = 0.5f;

    public VillagerTimetableScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        int original_width = 820;
        int original_height = 244;

        width = (int) (original_width * scale);
        height = (int) (original_height * scale);

        x = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - width / 2;
        y = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - height / 2;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta); // Drawables

        drawTimeTable();
        drawTimeIndicator(context);
        drawVillagers(context);
        drawTimeValues(context);
    }

    private void drawVillagers(GuiGraphics context) {
        context.pose().pushMatrix();
        context.pose().scale(scale, scale);

        context.blit(RenderPipelines.GUI_TEXTURED ,FARMER, (int) (x / scale + 8), (int) (y / scale + 25), 0, 0, 32, 64, 410, 820, 410, 820);
        context.blit(RenderPipelines.GUI_TEXTURED, NITWIT, (int) (x / scale + 8), (int) (y / scale + 95), 0, 0, 32, 64, 410, 820, 410, 820);
        context.blit(RenderPipelines.GUI_TEXTURED, BABY_VILLAGER, (int) (x / scale + 8), (int) (y / scale + 165), 0, 0, 32, 64, 410, 820, 410, 820);


        context.pose().popMatrix();
    }

    private void drawTimeTable() {
        TableWidget sleep_employed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 192, 64, (int) (x / scale + 46), (int) (y / scale + 25), 192, 64, 0 ,245, Component.nullToEmpty("test"));
        sleep_employed.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addRenderableWidget(sleep_employed);
        TableWidget idle_employed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 238), (int) (y / scale + 25), 64, 64, 192, 245, Component.nullToEmpty("test"));
        idle_employed.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addRenderableWidget(idle_employed);
        TableWidget work = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 224, 64, (int) (x / scale + 302), (int) (y / scale + 25), 224, 64, 256, 245, Component.nullToEmpty("test"));
        work.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".work")));
        addRenderableWidget(work);
        TableWidget socialize_employed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 526), (int) (y / scale + 25), 64, 64, 480, 245, Component.nullToEmpty("test"));
        socialize_employed.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".socialize")));
        addRenderableWidget(socialize_employed);
        TableWidget idle_employed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 32, 64, (int) (x / scale + 590), (int) (y / scale + 25), 32, 64, 544, 245, Component.nullToEmpty("test"));
        idle_employed2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addRenderableWidget(idle_employed2);
        TableWidget sleep_employed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 191, 64, (int) (x / scale + 622), (int) (y / scale + 25), 191, 64, 576, 245, Component.nullToEmpty("test"));
        sleep_employed2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addRenderableWidget(sleep_employed2);

        TableWidget sleep_unemployed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 192, 64, (int) (x / scale + 46), (int) (y / scale + 95), 192, 64, 0, 315, Component.nullToEmpty("test"));
        sleep_unemployed.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addRenderableWidget(sleep_unemployed);
        TableWidget idle_unemployed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 288, 64, (int) (x / scale + 238), (int) (y / scale + 95), 288, 64, 192, 315, Component.nullToEmpty("test"));
        idle_unemployed.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addRenderableWidget(idle_unemployed);
        TableWidget socialize_unemployed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 526), (int) (y / scale + 95), 64, 64, 480, 315, Component.nullToEmpty("test"));
        socialize_unemployed.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".socialize")));
        addRenderableWidget(socialize_unemployed);
        TableWidget idle_unemployed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 32, 64, (int) (x / scale + 590), (int) (y / scale + 95), 32, 64, 544, 315, Component.nullToEmpty("test"));
        idle_unemployed2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addRenderableWidget(idle_unemployed2);
        TableWidget sleep_unemployed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 191, 64, (int) (x / scale + 622), (int) (y / scale + 95), 191, 64, 576, 315, Component.nullToEmpty("test"));
        sleep_unemployed2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addRenderableWidget(sleep_unemployed2);

        TableWidget sleep_child = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 192, 64, (int) (x / scale + 46), (int) (y / scale + 165), 192, 64, 0, 385, Component.nullToEmpty("test"));
        sleep_child.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addRenderableWidget(sleep_child);
        TableWidget idle_child = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 96, 64, (int) (x / scale + 238), (int) (y / scale + 165), 96, 64, 192, 385, Component.nullToEmpty("test"));
        idle_child.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addRenderableWidget(idle_child);
        TableWidget play = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 96, 64, (int) (x / scale + 334), (int) (y / scale + 165), 96, 64, 288, 385, Component.nullToEmpty("test"));
        play.setTooltip(Tooltip.create(Component.translatable( "tooltip." + VillagerTimetable.MODID + ".play")));
        addRenderableWidget(play);
        TableWidget idle_child2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 128, 64, (int) (x / scale + 430), (int) (y / scale + 165), 128, 64, 384, 385, Component.nullToEmpty("test"));
        idle_child2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addRenderableWidget(idle_child2);
        TableWidget play2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 558), (int) (y / scale + 165), 64, 64, 512, 385, Component.nullToEmpty("test"));
        play2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".play")));
        addRenderableWidget(play2);
        TableWidget sleep_child2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 191, 64, (int) (x / scale + 622), (int) (y / scale + 165), 191, 64, 576, 385, Component.nullToEmpty("test"));
        sleep_child2.setTooltip(Tooltip.create(Component.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addRenderableWidget(sleep_child2);
    }
//
//    int[] convertTextureCoord(int x, int y) {
//        int[] newCoord = new int[2];
//
//        newCoord[0] = this.x + (int)(scale * x);
//        newCoord[1] = this.y + (int)(scale * y);
//
//        return newCoord;
//    }

    int intLerp(int i1, int i2, float frac) {
        return (int) ((1 - frac) * i1 + frac * i2);
    }

    private void drawTimeIndicator(GuiGraphics context) {
        assert Minecraft.getInstance().level != null;
        float timeFrac = ((Minecraft.getInstance().level.getDayTime() + 6000) % 24000)/ 24000f;

        int time_x = intLerp(46, 810, timeFrac);
        context.pose().pushMatrix();
        context.pose().scale(scale, scale);
        context.blit(RenderPipelines.GUI_TEXTURED, SCREEN_TEXTURE, (int) (x / scale + time_x), (int) (y / scale + 8),
                821,
                0,
                3,
                221,
                3, 221, 1024, 1024);

        context.pose().popMatrix();
    }

    private void drawTimeValues(GuiGraphics context){
        context.pose().pushMatrix();
        context.pose().scale(scale, scale);

        for (int i = 0; i < 24; i++) {
            Component timeText = null;
            Option.TimeFormatOption.TimeFormat timeFormat = VillagerTimetableConfig.INSTANCE.timeFormat.get();
            switch (timeFormat){
                case H24 -> {
                    if (i < 10) {
                        timeText = Component.nullToEmpty(String.format("0%dh", i));
                    } else {
                        timeText = Component.nullToEmpty(String.format("%dh", i));
                    }
                }
                case H12 -> {
                    int time = i % 12;
                    if (time == 0) time = 12;

                    String suffix;
                    if (i < 12) suffix = "am";
                    else suffix = "pm";

                    timeText = Component.nullToEmpty(String.format("%d%s", time, suffix));
                }
                case TICKS -> {
                    int time = ((i - 6) % 24);
                    if (time < 0) time += 24;
                    timeText = Component.nullToEmpty(String.format("%dk", time));
                }
            }

            int offset = (int) ((32 - font.width(timeText) ) / (1 / scale));
            context.drawString(font, timeText, (int) ((1 / scale) * x + 46 + offset + i * 32), (int)( (1 / scale) * y + 9), 0xFF4a4a4a, false);
        }
        context.pose().popMatrix();
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);

        context.blit(RenderPipelines.GUI_TEXTURED, SCREEN_TEXTURE, x, y, 0, 0, width, height, 820, 244, 1024, 1024);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent input) {
        if (KeyBinds.openTimetable.matches(input)) {
            this.onClose();
            return true;
        }

        return super.keyPressed(input);
    }
}
