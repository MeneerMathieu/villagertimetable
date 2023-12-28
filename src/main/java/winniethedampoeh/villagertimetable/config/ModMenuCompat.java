package winniethedampoeh.villagertimetable.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return new ConfigScreenFactory<Screen>() {
            @Override
            public Screen create(Screen parent) {
                return new VillagerTimetableConfigScreen(parent, VillagerTimetableConfig.INSTANCE);
            }
        };
    }
}
