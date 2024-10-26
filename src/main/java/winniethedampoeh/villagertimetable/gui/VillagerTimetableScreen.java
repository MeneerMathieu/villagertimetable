package winniethedampoeh.villagertimetable.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import winniethedampoeh.villagertimetable.VillagerTimetable;
import winniethedampoeh.villagertimetable.config.Option;
import winniethedampoeh.villagertimetable.config.VillagerTimetableConfig;
import winniethedampoeh.villagertimetable.control.KeyBinds;

public class VillagerTimetableScreen extends Screen {
    public static final Identifier SCREEN_TEXTURE = Identifier.tryParse(VillagerTimetable.MODID,
            "textures/screen/timetable.png");
    public static final Identifier BABY_VILLAGER = Identifier.tryParse(VillagerTimetable.MODID,
            "textures/screen/baby_villager.png");
    public static final Identifier NITWIT = Identifier.tryParse(VillagerTimetable.MODID,
            "textures/screen/nitwit.png");
    public static final Identifier FARMER = Identifier.tryParse(VillagerTimetable.MODID,
            "textures/screen/farmer.png");

    private int x;
    private int y;
    private int width;
    private int height;

    private final float scale = 0.5f;

    public VillagerTimetableScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int original_width = 820;
        int original_height = 244;

        width = (int) (original_width * scale);
        height = (int) (original_height * scale);

        x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 - width / 2;
        y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2 - height / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, scale); // Drawables

        drawTimeTable(context, mouseX, mouseY, delta);
        drawTimeIndicator(context);
        drawTimeValues(context);
        drawVillagers(context);

    }

    private void drawVillagers(DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1f);

        context.drawTexture(RenderLayer::getGuiTextured ,FARMER, (int) (x / scale + 8), (int) (y / scale + 25), 0, 0, 32, 64, 410, 820, 410, 820);
        context.drawTexture(RenderLayer::getGuiTextured, NITWIT, (int) (x / scale + 8), (int) (y / scale + 95), 0, 0, 32, 64, 410, 820, 410, 820);
        context.drawTexture(RenderLayer::getGuiTextured, BABY_VILLAGER, (int) (x / scale + 8), (int) (y / scale + 165), 0, 0, 32, 64, 410, 820, 410, 820);


        context.getMatrices().pop();
    }

    private void drawTimeTable(DrawContext context, int mouseX, int mouseY, float delta) {
        TableWidget sleep_employed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 192, 64, (int) (x / scale + 46), (int) (y / scale + 25), 192, 64, 0 ,245, Text.of("test"));
        sleep_employed.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addDrawableChild(sleep_employed);
        TableWidget idle_employed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 238), (int) (y / scale + 25), 64, 64, 192, 245, Text.of("test"));
        idle_employed.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addDrawableChild(idle_employed);
        TableWidget work = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 224, 64, (int) (x / scale + 302), (int) (y / scale + 25), 224, 64, 256, 245, Text.of("test"));
        work.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".work")));
        addDrawableChild(work);
        TableWidget socialize_employed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 526), (int) (y / scale + 25), 64, 64, 480, 245, Text.of("test"));
        socialize_employed.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".socialize")));
        addDrawableChild(socialize_employed);
        TableWidget idle_employed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 32, 64, (int) (x / scale + 590), (int) (y / scale + 25), 32, 64, 544, 245, Text.of("test"));
        idle_employed2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addDrawableChild(idle_employed2);
        TableWidget sleep_employed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 191, 64, (int) (x / scale + 622), (int) (y / scale + 25), 191, 64, 576, 245, Text.of("test"));
        sleep_employed2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addDrawableChild(sleep_employed2);

        TableWidget sleep_unemployed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 192, 64, (int) (x / scale + 46), (int) (y / scale + 95), 192, 64, 0, 315, Text.of("test"));
        sleep_unemployed.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addDrawableChild(sleep_unemployed);
        TableWidget idle_unemployed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 288, 64, (int) (x / scale + 238), (int) (y / scale + 95), 288, 64, 192, 315, Text.of("test"));
        idle_unemployed.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addDrawableChild(idle_unemployed);
        TableWidget socialize_unemployed = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 526), (int) (y / scale + 95), 64, 64, 480, 315, Text.of("test"));
        socialize_unemployed.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".socialize")));
        addDrawableChild(socialize_unemployed);
        TableWidget idle_unemployed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 32, 64, (int) (x / scale + 590), (int) (y / scale + 95), 32, 64, 544, 315, Text.of("test"));
        idle_unemployed2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addDrawableChild(idle_unemployed2);
        TableWidget sleep_unemployed2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 191, 64, (int) (x / scale + 622), (int) (y / scale + 95), 191, 64, 576, 315, Text.of("test"));
        sleep_unemployed2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addDrawableChild(sleep_unemployed2);

        TableWidget sleep_child = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 192, 64, (int) (x / scale + 46), (int) (y / scale + 165), 192, 64, 0, 385, Text.of("test"));
        sleep_child.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addDrawableChild(sleep_child);
        TableWidget idle_child = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 96, 64, (int) (x / scale + 238), (int) (y / scale + 165), 96, 64, 192, 385, Text.of("test"));
        idle_child.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addDrawableChild(idle_child);
        TableWidget play = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 96, 64, (int) (x / scale + 334), (int) (y / scale + 165), 96, 64, 288, 385, Text.of("test"));
        play.setTooltip(Tooltip.of(Text.translatable( "tooltip." + VillagerTimetable.MODID + ".play")));
        addDrawableChild(play);
        TableWidget idle_child2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 128, 64, (int) (x / scale + 430), (int) (y / scale + 165), 128, 64, 384, 385, Text.of("test"));
        idle_child2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".idle")));
        addDrawableChild(idle_child2);
        TableWidget play2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 64, 64, (int) (x / scale + 558), (int) (y / scale + 165), 64, 64, 512, 385, Text.of("test"));
        play2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".play")));
        addDrawableChild(play2);
        TableWidget sleep_child2 = new TableWidget(scale, SCREEN_TEXTURE, 1024, 1024, 191, 64, (int) (x / scale + 622), (int) (y / scale + 165), 191, 64, 576, 385, Text.of("test"));
        sleep_child2.setTooltip(Tooltip.of(Text.translatable("tooltip." + VillagerTimetable.MODID + ".sleep")));
        addDrawableChild(sleep_child2);
    }

    int[] convertTextureCoord(int x, int y) {
        int[] newCoord = new int[2];

        newCoord[0] = this.x + (int)(scale * x);
        newCoord[1] = this.y + (int)(scale * y);

        return newCoord;
    }

    int intLerp(int i1, int i2, float frac) {
        return (int) ((1 - frac) * i1 + frac * i2);
    }

    private void drawTimeIndicator(DrawContext context) {
        assert MinecraftClient.getInstance().world != null;
        float timeFrac = ((MinecraftClient.getInstance().world.getTimeOfDay() + 6000) % 24000)/ 24000f;

        int time_x = intLerp(46, 810, timeFrac);
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1f);
        context.drawTexture(RenderLayer::getGuiTextured, SCREEN_TEXTURE, (int) (x / scale + time_x), (int) (y / scale + 8),
                821,
                0,
                3,
                221,
                3, 221, 1024, 1024);

        context.getMatrices().pop();
    }

    private void drawTimeValues(DrawContext context){
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1f);

        for (int i = 0; i < 24; i++) {
            Text timeText = null;
            Option.TimeFormatOption.TimeFormat timeFormat = VillagerTimetableConfig.INSTANCE.timeFormat.get();
            switch (timeFormat){
                case H24 -> {
                    if (i < 10) {
                        timeText = Text.of(String.format("0%dh", i));
                    } else {
                        timeText = Text.of(String.format("%dh", i));
                    }
                }
                case H12 -> {
                    int time = i;
                    time = i % 12;
                    if (time == 0) time = 12;

                    String suffix;
                    if (i < 12) suffix = "am";
                    else suffix = "pm";

                    timeText = Text.of(String.format("%d%s", time, suffix));
                }
                case TICKS -> {
                    int time = ((i - 6) % 24);
                    if (time < 0) time += 24;
                    if (time < 10) timeText = Text.of(String.valueOf(time * 1000));
                    else timeText = Text.of(String.format("%dk", time));
                }
            }

            int offset = (int) ((32 - textRenderer.getWidth(timeText) ) / (1 / scale));
            context.drawText(textRenderer, timeText, (int) ((1 / scale) * x + 46 + offset + i * 32), (int)( (1 / scale) * y + 9), 0x4a4a4a, false);
        }
        context.getMatrices().pop();
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(RenderLayer::getGuiTextured, SCREEN_TEXTURE, x, y, 0, 0, width, height, 820, 244, 1024, 1024);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeyBinds.openTimetable.matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
