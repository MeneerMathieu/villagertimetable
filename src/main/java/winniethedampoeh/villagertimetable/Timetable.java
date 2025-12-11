package winniethedampoeh.villagertimetable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ai.brain.Activity;

public class Timetable{


    public static Activity getCurrentActivity(VillagerType villagerType) {
        /*  Since 1.21.11 the schedule is no longer visible from the client.
            It can now be modified using datapacks. To keep this mod client side,
            I will assume that the schedule was not changed using data packs.
            */
        assert MinecraftClient.getInstance().world != null;
        long tick = MinecraftClient.getInstance().world.getTimeOfDay() % 24000;

        switch (villagerType) {
            case CHILD -> {
                return BabyActivityAtTick(tick);
            }
            case UNEMPLOYED -> {
                return UnemployedActivityAtTick(tick);
            }
            case EMPLOYED -> {
                return EmployedActivityAtTick(tick);
            }
            default -> throw new IllegalStateException("Unexpected value: " + villagerType);
        }

    }

    private static Activity BabyActivityAtTick(long tick) {
        if (tick < 10) {
            return Activity.REST;
        } else if (tick < 3000) {
            return Activity.IDLE;
        } else if (tick < 6000) {
            return  Activity.PLAY;
        } else if (tick < 10000) {
            return Activity.IDLE;
        } else if (tick < 12000) {
            return Activity.PLAY;
        } else {
            return Activity.REST;
        }
    }

    private static Activity UnemployedActivityAtTick(long tick) {
        if (tick < 10) {
            return Activity.REST;
        } else if (tick < 9000) {
            return Activity.IDLE;
        } else if (tick < 11000) {
            return Activity.MEET;
        } else if (tick < 12000) {
            return Activity.IDLE;
        } else {
            return Activity.REST;
        }
    }

    private static Activity EmployedActivityAtTick(long tick) {
       if (tick >= 2000 && tick < 9000) {
           return Activity.WORK;
       }
       return UnemployedActivityAtTick(tick);
    }

    public enum VillagerType{
        EMPLOYED,
        UNEMPLOYED,
        CHILD
    }
}

