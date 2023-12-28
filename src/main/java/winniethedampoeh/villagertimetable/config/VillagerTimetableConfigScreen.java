package winniethedampoeh.villagertimetable.config;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import winniethedampoeh.villagertimetable.VillagerTimetable;

public class VillagerTimetableConfigScreen extends Screen {
    private final Screen parent;
    private final VillagerTimetableConfig config;

    private List<Value<?>> values;

    public VillagerTimetableConfigScreen(Screen parent, VillagerTimetableConfig config) {
        super(Text.translatable(getTranslationKey("title")));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        Value<Boolean> guiRendering = new Value<>(config.guiRendering);
        Value<Option.TimeFormatOption.TimeFormat> timeFormat = new Value<>(config.timeFormat);
        Value<Option.RenderLocationOption.RenderLocation> renderLocation = new Value<>(config.renderLocation);

        values = List.of(guiRendering, timeFormat, renderLocation);

        addDrawableChild(createBooleanValueButton(guiRendering,
                width / 2 - 100 - 110, height / 2 - 10 - 12, 200, 20));
        addDrawableChild(createTimeFormatButton(timeFormat,
                width / 2 - 100 - 110, height / 2 - 10 + 12, 200, 20));
        addDrawableChild(createRenderLocationButton(renderLocation,
                width / 2 - 100 + 110, height / 2 - 10 - 12, 200, 20));

        addDrawableChild(new ButtonWidget.Builder(ScreenTexts.DONE, button -> {
            saveValues();
            close();
        }).dimensions(width / 2 - 75 - 79, height - 40, 150, 20).build());

        addDrawableChild(new ButtonWidget.Builder(ScreenTexts.CANCEL, button -> {
            close();
        }).dimensions(width / 2 - 75 + 79, height - 40, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    private void saveValues() {
        for (Value<?> value : values) {
            if (value.isChanged()) {
                value.saveToOption();
            }
        }

        config.save();
    }

    private static String getTranslationKey(String optionKey) {
        return "options." + VillagerTimetable.MODID + "." + optionKey;
    }

    private static String getTooltipKey(String translationKey) {
        return translationKey + ".tooltip";
    }

    private ButtonWidget createBooleanValueButton(Value<Boolean> value, int x, int y, int width, int height) {
        String translationKey = getTranslationKey(value.getOption().getKey());
        Text text = Text.translatable(translationKey);
        Text tooltipText = Text.translatable(getTooltipKey(translationKey));

        return new ButtonWidget.Builder(
                ScreenTexts.composeGenericOptionText(text, ScreenTexts.onOrOff(value.get())),
                button -> {
                    boolean newValue = !value.get();
                    value.set(newValue);
                    Text valueText = ScreenTexts.onOrOff(newValue);
                    if (value.isChanged()) {
                        valueText = valueText.copy().styled(style -> style.withBold(true));
                    }
                    button.setMessage(ScreenTexts.composeGenericOptionText(text, valueText));
                }
        )
                .tooltip(Tooltip.of(tooltipText))
                .dimensions(x, y, width, height)
                .build();
    }

    private ButtonWidget createTimeFormatButton(Value<Option.TimeFormatOption.TimeFormat> value, int x, int y, int width, int height){
        String translationKey = getTranslationKey(value.getOption().getKey());
        MutableText text = Text.translatable(translationKey);
        MutableText tooltipText = Text.translatable(getTooltipKey(translationKey));

        return new ButtonWidget.Builder(
                ScreenTexts.composeGenericOptionText(text, Text.translatable(value.get().getTranslationKey())),
                button -> {
                    Option.TimeFormatOption.TimeFormat newValue = Option.TimeFormatOption.TimeFormat.values()[(value.get().getOrdinal() + 1) % (value.get().getMaxOrdinal() + 1)];
                    value.set(newValue);
                    Text valueText = Text.translatable(value.get().getTranslationKey());
                    if (value.isChanged()) {
                        valueText = valueText.copy().styled(style -> style.withBold(true));
                    }
                    button.setMessage(ScreenTexts.composeGenericOptionText(text, valueText));
                }
        )
                .tooltip(Tooltip.of(tooltipText))
                .dimensions(x, y, width, height)
                .build();
    }

    private ButtonWidget createRenderLocationButton(Value<Option.RenderLocationOption.RenderLocation> value, int x, int y, int width, int height){
        String translationKey = getTranslationKey(value.getOption().getKey());
        MutableText text = Text.translatable(translationKey);
        MutableText tooltipText = Text.translatable(getTooltipKey(translationKey));

        return new ButtonWidget.Builder(
                ScreenTexts.composeGenericOptionText(text, Text.translatable(value.get().getTranslationKey())),
                button -> {
                    Option.RenderLocationOption.RenderLocation newValue = Option.RenderLocationOption.RenderLocation.values()[(value.get().getOrdinal() + 1) % (value.get().getMaxOrdinal() + 1)];
                    value.set(newValue);
                    Text valueText = Text.translatable(value.get().getTranslationKey());
                    if (value.isChanged()) {
                        valueText = valueText.copy().styled(style -> style.withBold(true));
                    }
                    button.setMessage(ScreenTexts.composeGenericOptionText(text, valueText));
                }
        )
                .tooltip(Tooltip.of(tooltipText))
                .dimensions(x, y, width, height)
                .build();
    }

    private static class Value<T> {
        private final Option<T> option;
        private final T originalValue;
        private T value;

        public Value(Option<T> option) {
            this.option = option;
            originalValue = this.option.get();
            value = originalValue;
        }

        public Option<T> getOption() {
            return option;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }

        public boolean isChanged() {
            return !value.equals(originalValue);
        }

        public void saveToOption() {
            option.set(value);
        }
    }
}
