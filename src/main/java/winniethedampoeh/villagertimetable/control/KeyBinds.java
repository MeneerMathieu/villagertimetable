package winniethedampoeh.villagertimetable.control;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import winniethedampoeh.villagertimetable.VillagerTimetable;
import winniethedampoeh.villagertimetable.gui.VillagerTimetableScreen;

public class KeyBinds {

        public static KeyBinding openTimetable;

        public static void registerKeyBinds(){
            openTimetable = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "key." + VillagerTimetable.MODID + ".open_timetable",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    "category." + VillagerTimetable.MODID
            ));


            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (openTimetable.wasPressed()) {
                    MinecraftClient.getInstance().setScreen(new VillagerTimetableScreen(Text.of("TEST")));
                }
            });
        }

}
