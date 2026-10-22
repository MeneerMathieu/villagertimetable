package winniethedampoeh.villagertimetable.control;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import winniethedampoeh.villagertimetable.VillagerTimetable;
import winniethedampoeh.villagertimetable.gui.VillagerTimetableScreen;

public class KeyBinds {

        public static KeyMapping openTimetable;
        public static KeyMapping.Category MOD_CATEGORY = new KeyMapping.Category(Identifier.parse(VillagerTimetable.MODID));

        public static void registerKeyBinds(){
            openTimetable = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                    "key." + VillagerTimetable.MODID + ".open_timetable",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    MOD_CATEGORY
            ));

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (openTimetable.consumeClick()) {
                    Minecraft.getInstance().gui.setScreen(new VillagerTimetableScreen(Component.nullToEmpty("Timetable")));
                }
            });
        }

}
