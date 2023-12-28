package winniethedampoeh.villagertimetable;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winniethedampoeh.villagertimetable.control.KeyBinds;
import winniethedampoeh.villagertimetable.gui.VillagerScheduleOverlay;

public class VillagerTimetable implements ClientModInitializer {
	public static final String MODID = "villagertimetable";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();


	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello Fabric world!");

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		HudRenderCallback.EVENT.register(new VillagerScheduleOverlay());
		LOGGER.info("Hud done!");

		KeyBinds.registerKeyBinds();
		LOGGER.info("Keybinds done!");

		LOGGER.info("Config done!");
	}
}