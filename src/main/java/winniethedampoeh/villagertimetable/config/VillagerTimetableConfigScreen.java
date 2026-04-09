package winniethedampoeh.villagertimetable.config;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import winniethedampoeh.villagertimetable.VillagerTimetable;

public class VillagerTimetableConfigScreen extends Screen {
    private final Screen parent;
    private final VillagerTimetableConfig config;

    private List<Value<?>> values;

    public VillagerTimetableConfigScreen(Screen parent, VillagerTimetableConfig config) {
        super(Component.translatable(getTranslationKey("title")));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        Value<Boolean> guiRendering = new Value<>(config.guiRendering);
        Value<Option.TimeFormatOption.TimeFormat> timeFormat = new Value<>(config.timeFormat);
        Value<Option.RenderLocationOption.RenderLocation> renderLocation = new Value<>(config.renderLocation);

        values = List.of(guiRendering, timeFormat, renderLocation);

        addRenderableWidget(createBooleanValueButton(guiRendering,
                width / 2 - 100 - 110, height / 2 - 10 - 12, 200, 20));
        addRenderableWidget(createTimeFormatButton(timeFormat,
                width / 2 - 100 - 110, height / 2 - 10 + 12, 200, 20));
        addRenderableWidget(createRenderLocationButton(renderLocation,
                width / 2 - 100 + 110, height / 2 - 10 - 12, 200, 20));

        addRenderableWidget(new Button.Builder(CommonComponents.GUI_DONE, button -> {
            saveValues();
            onClose();
        }).bounds(width / 2 - 75 - 79, height - 40, 150, 20).build());

        addRenderableWidget(new Button.Builder(CommonComponents.GUI_CANCEL, button -> {
            onClose();
        }).bounds(width / 2 - 75 + 79, height - 40, 150, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.centeredText(this.font, this.title, this.width / 2, 5, 0xffffff);
    }


    @Override
    public void onClose() {
        minecraft.setScreen(parent);
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

    private Button createBooleanValueButton(Value<Boolean> value, int x, int y, int width, int height) {
        String translationKey = getTranslationKey(value.getOption().getKey());
        Component text = Component.translatable(translationKey);
        Component tooltipText = Component.translatable(getTooltipKey(translationKey));

        return new Button.Builder(
                CommonComponents.optionNameValue(text, CommonComponents.optionStatus(value.get())),
                button -> {
                    boolean newValue = !value.get();
                    value.set(newValue);
                    Component valueText = CommonComponents.optionStatus(newValue);
                    if (value.isChanged()) {
                        valueText = valueText.copy().withStyle(style -> style.withBold(true));
                    }
                    button.setMessage(CommonComponents.optionNameValue(text, valueText));
                }
        )
                .tooltip(Tooltip.create(tooltipText))
                .bounds(x, y, width, height)
                .build();
    }

    private Button createTimeFormatButton(Value<Option.TimeFormatOption.TimeFormat> value, int x, int y, int width, int height){
        String translationKey = getTranslationKey(value.getOption().getKey());
        MutableComponent text = Component.translatable(translationKey);
        MutableComponent tooltipText = Component.translatable(getTooltipKey(translationKey));

        return new Button.Builder(
                CommonComponents.optionNameValue(text, Component.translatable(value.get().getTranslationKey())),
                button -> {
                    Option.TimeFormatOption.TimeFormat newValue = Option.TimeFormatOption.TimeFormat.values()[(value.get().getOrdinal() + 1) % (value.get().getMaxOrdinal() + 1)];
                    value.set(newValue);
                    Component valueText = Component.translatable(value.get().getTranslationKey());
                    if (value.isChanged()) {
                        valueText = valueText.copy().withStyle(style -> style.withBold(true));
                    }
                    button.setMessage(CommonComponents.optionNameValue(text, valueText));
                }
        )
                .tooltip(Tooltip.create(tooltipText))
                .bounds(x, y, width, height)
                .build();
    }

    private Button createRenderLocationButton(Value<Option.RenderLocationOption.RenderLocation> value, int x, int y, int width, int height){
        String translationKey = getTranslationKey(value.getOption().getKey());
        MutableComponent text = Component.translatable(translationKey);
        MutableComponent tooltipText = Component.translatable(getTooltipKey(translationKey));

        return new Button.Builder(
                CommonComponents.optionNameValue(text, Component.translatable(value.get().getTranslationKey())),
                button -> {
                    Option.RenderLocationOption.RenderLocation newValue = Option.RenderLocationOption.RenderLocation.values()[(value.get().getOrdinal() + 1) % (value.get().getMaxOrdinal() + 1)];
                    value.set(newValue);
                    Component valueText = Component.translatable(value.get().getTranslationKey());
                    if (value.isChanged()) {
                        valueText = valueText.copy().withStyle(style -> style.withBold(true));
                    }
                    button.setMessage(CommonComponents.optionNameValue(text, valueText));
                }
        )
                .tooltip(Tooltip.create(tooltipText))
                .bounds(x, y, width, height)
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
