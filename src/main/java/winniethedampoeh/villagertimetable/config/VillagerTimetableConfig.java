package winniethedampoeh.villagertimetable.config;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;
import winniethedampoeh.villagertimetable.VillagerTimetable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Map;

import static winniethedampoeh.villagertimetable.VillagerTimetable.GSON;
import static winniethedampoeh.villagertimetable.VillagerTimetable.MODID;

public class VillagerTimetableConfig {
    public static final VillagerTimetableConfig INSTANCE = new VillagerTimetableConfig(FabricLoader.getInstance().getConfigDir().resolve(MODID + ".json").toFile());
    static {
        INSTANCE.load();
    }

    public static void loadConfig() {
        // Needs no body
    }

    protected final File file;
    protected final Object2ObjectLinkedOpenHashMap<String, Option<?>> optionMap = new Object2ObjectLinkedOpenHashMap<>();
    protected final Map<String, Option<?>> optionMapView = Collections.unmodifiableMap(optionMap);

    public final Option.BooleanOption guiRendering = addOption(new Option.BooleanOption("hud_rendering", true));
    public final Option.TimeFormatOption timeFormat = addOption(new Option.TimeFormatOption("time_format", Option.TimeFormatOption.TimeFormat.H24));
    public final Option.RenderLocationOption renderLocation = addOption(new Option.RenderLocationOption("render_location", Option.RenderLocationOption.RenderLocation.SOUTH_WEST));
    public VillagerTimetableConfig(File file) {
        this.file = file;
    }

    public void load() {
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                fromJson(JsonParser.parseReader(reader));
            } catch (Exception e) {
                VillagerTimetable.LOGGER.error("Could not load config from file '" + file.getAbsolutePath() + "'", e);
            }
        }
        save();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(toJson(), writer);
        } catch (Exception e) {
            VillagerTimetable.LOGGER.error("Could not save config to file '" + file.getAbsolutePath() + "'", e);
        }
    }

    protected void fromJson(JsonElement json) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            ObjectBidirectionalIterator<Object2ObjectMap.Entry<String, Option<?>>> iterator = optionMap.object2ObjectEntrySet().fastIterator();
            while (iterator.hasNext()) {
                Object2ObjectMap.Entry<String, Option<?>> entry = iterator.next();
                JsonElement element = object.get(entry.getKey());
                if (element != null) {
                    try {
                        entry.getValue().fromJson(element);
                    } catch (JsonParseException e) {
                        VillagerTimetable.LOGGER.error("Could not read option '" + entry.getKey() + "'", e);
                    }
                }
            }
        } else {
            throw new JsonParseException("Json must be an object");
        }
    }

    protected JsonElement toJson() {
        JsonObject object = new JsonObject();
        ObjectBidirectionalIterator<Object2ObjectMap.Entry<String, Option<?>>> iterator = optionMap.object2ObjectEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Object2ObjectMap.Entry<String, Option<?>> entry = iterator.next();
            object.add(entry.getKey(), entry.getValue().toJson());
        }
        return object;
    }

    protected <T extends Option<?>> T addOption(T option) {
        Option<?> old = optionMap.put(option.getKey(), option);
        if (old != null) {
            VillagerTimetable.LOGGER.warn("Option with key '" + old.getKey() + "' was overridden");
        }
        return option;
    }

    @Nullable
    public Option<?> getOption(String key) {
        return optionMap.get(key);
    }

    public Map<String, Option<?>> getOptionMapView() {
        return optionMapView;
    }

}
