package winniethedampoeh.villagertimetable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;

public class Timetable {

    public static Activity getCurrentActivity(VillagerType villagerType) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return Activity.IDLE;
        if (client.world == null) {
            VillagerTimetable.LOGGER.error("Could not access the client world");
            return null;
        }

        int currentTime = (int) (client.world.getTimeOfDay() % 24000);
        switch (villagerType) {
            case CHILD -> {
                return Schedule.VILLAGER_BABY.getActivityForTime(currentTime);
            }
            case EMPLOYED -> {
                return Schedule.VILLAGER_DEFAULT.getActivityForTime(currentTime);
            }
            case UNEMPLOYED -> {
                Activity activity = Schedule.VILLAGER_DEFAULT.getActivityForTime(currentTime);
                if (activity.equals(Activity.WORK)) return Activity.IDLE;
                return activity;
            }
        }
        return Activity.IDLE;
    }

    public enum VillagerType{
        EMPLOYED,
        UNEMPLOYED,
        CHILD
    }
}
