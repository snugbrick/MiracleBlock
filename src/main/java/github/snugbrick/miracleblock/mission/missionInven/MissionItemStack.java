package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.api.Standardization;
import github.snugbrick.miracleblock.mission.MissionStatus;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 21:26
 */
public class MissionItemStack extends ItemStack {
    private static final Map<String, MissionItemStack> nbtGetMissionItemStack = new HashMap<>();
    private static final Map<ItemStack, String> missionItemStackGetNbt = new HashMap<>();
    private static final Map<MissionItemStack, MissionStatus> missionItemStackStatus = new HashMap<>();

    public MissionItemStack(ItemStack icon) {
        super(icon);
    }

    public static void registerMissionItemStack(MissionItemStack icon, String key) {
        nbtGetMissionItemStack.put(key, icon);
        missionItemStackGetNbt.put(icon, key);
    }


    public static void registerMissionStatus(MissionItemStack icon, MissionStatus status) {
        missionItemStackStatus.put(icon, status);
    }

    public static MissionStatus getMissionItemStackStatus(MissionItemStack itemStack) {
        return missionItemStackStatus.get(itemStack);
    }

    public static String getNBT(ItemStack itemStack) {
        return missionItemStackGetNbt.get(itemStack);
    }

    public static MissionItemStack getMissionItemStack(String key) {
        return nbtGetMissionItemStack.get(key);
    }

    public static MissionItemStack toIcon(ItemStack icon, String nbt, MissionStatus status) {
        MissionItemStack mis = toIcon(icon, nbt);
        registerMissionStatus(mis, status);

        return mis;
    }

    public static MissionItemStack toIcon(ItemStack icon, String nbt) {
        MissionItemStack mis = new MissionItemStack(Standardization.missionStandardization(icon, nbt, nbt));
        registerMissionItemStack(mis, nbt);

        return mis;
    }

    public static boolean isSameIcon(MissionItemStack fromIcon, MissionItemStack aimIcon) {
        return fromIcon.equals(aimIcon);
    }


}
