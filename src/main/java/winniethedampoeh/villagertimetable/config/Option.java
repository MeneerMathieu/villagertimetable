package winniethedampoeh.villagertimetable.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import winniethedampoeh.villagertimetable.VillagerTimetable;

public interface Option<T> {
    String getKey();

    T get();

    void set(T value);

    JsonElement toJson();

    void fromJson(JsonElement json) throws JsonParseException;

    abstract class BaseOption<T> implements Option<T> {
        protected final String key;
        protected T value;

        public BaseOption(String key, T defaultValue) {
            this.key = key;
            value = defaultValue;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public void set(T value) {
            this.value = value;
        }
    }

    class BooleanOption extends BaseOption<Boolean> {
        public BooleanOption(String key, Boolean defaultValue) {
            super(key, defaultValue);
        }

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(get());
        }

        @Override
        public void fromJson(JsonElement json) throws JsonParseException {
            if (json.isJsonPrimitive()) {
                set(json.getAsBoolean());
            } else {
                throw new JsonParseException("Json must be a primitive");
            }
        }
    }

    class TimeFormatOption extends BaseOption<TimeFormatOption.TimeFormat> {

        public TimeFormatOption(String key, TimeFormat defaultValue) {
            super(key, defaultValue);
        }

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(
                    switch (value) {
                        case H24 -> "24h";
                        case H12 -> "12h";
                        case TICKS -> "ticks";
                    }
            );
        }

        @Override
        public void fromJson(JsonElement json) throws JsonParseException {
            switch (json.getAsString()) {
                case "24h" -> set(TimeFormat.H24);
                case "12h" -> set(TimeFormat.H12);
                case "ticks" -> set(TimeFormat.TICKS);
                default -> throw new JsonParseException("Invalid json argument. Corract options are \"24h\", \"12h\" and \"ticks\"");
            }
        }

        public enum TimeFormat implements EnumOption {
            H24("24h"),
            H12("12h"),
            TICKS("ticks");

            private final String valueKey;

            TimeFormat(String valueKey) {
                this.valueKey = valueKey;
            }

            @Override
            public int getMaxOrdinal() {
                return TICKS.ordinal();
            }

            @Override
            public String getValueKey() {
                return valueKey;
            }

            @Override
            public String getTranslationKey() {
                return String.format("options.%s.time_format.%s", VillagerTimetable.MODID, getValueKey());
            }

            @Override
            public int getOrdinal() {
                return this.ordinal();
            }
        }
    }

    class RenderLocationOption extends BaseOption<RenderLocationOption.RenderLocation> {

        public RenderLocationOption(String key, RenderLocation defaultValue) {
            super(key, defaultValue);
        }

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(value.getValueKey());
        }

        @Override
        public void fromJson(JsonElement json) throws JsonParseException {
            switch (json.getAsString()) {
                case "north_east" -> set(RenderLocation.NORTH_EAST);
                case "north_west" -> set(RenderLocation.NORTH_WEST);
                case "south_east" -> set(RenderLocation.SOUTH_EAST);
                case "south_west" -> set(RenderLocation.SOUTH_WEST);
                case "hotbar_left" -> set(RenderLocation.HOTBAR_LEFT);
                case "hotbar_right" -> set(RenderLocation.HOTBAR_RIGHT);
                default -> throw new JsonParseException("Invalid json argument, correct json arguments are: \"north_east\", \"north_west\", \"south_east\", \"south_west\", \"hotbar_left\" and \"hotbar_right\"");
            }
        }

        public enum RenderLocation implements EnumOption {
            NORTH_EAST("north_east"),
            NORTH_WEST("north_west"),
            SOUTH_EAST("south_east"),
            SOUTH_WEST("south_west"),
            HOTBAR_LEFT("hotbar_left"),
            HOTBAR_RIGHT("hotbar_right");
            private final String valueKey;
            RenderLocation(String valueKey) {
                this.valueKey = valueKey;
            }

            @Override
            public int getOrdinal() {
                return this.ordinal();
            }

            @Override
            public int getMaxOrdinal() {
                return HOTBAR_RIGHT.getOrdinal();
            }

            @Override
            public String getValueKey() {
                return valueKey;
            }

            @Override
            public String getTranslationKey() {
                return String.format("options.%s.render_location.%s", VillagerTimetable.MODID, getValueKey());
            }
        }
    }

    interface EnumOption {
        int getOrdinal();
        int getMaxOrdinal();
        String getValueKey();
        String getTranslationKey();

    }
}

