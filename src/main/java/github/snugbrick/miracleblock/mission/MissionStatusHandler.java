package github.snugbrick.miracleblock.mission;

import github.snugbrick.miracleblock.mission.missionInven.MissionItemStack;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.inventory.ItemStack;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.28 18:14
 */
public class MissionStatusHandler extends Mission {

    private MissionType missionType;

    private MissionStatus missionStatus;

    private MissionItemStack missionItemStack;

    public static MissionStatus getItemStackStatus(ItemStack itemStack) {
        if (AboutNBT.hasCustomNBT(itemStack, "UNDONE")) {
            return MissionStatus.UNDONE;
        } else if (AboutNBT.hasCustomNBT(itemStack, "COMPLETED")) {
            return MissionStatus.COMPLETED;
        } else if (AboutNBT.hasCustomNBT(itemStack, "COLLECTED")) {
            return MissionStatus.COLLECTED;
        } else {
            return null;
        }
    }

    public static MissionItemStack getMissionIcon(MissionStatus missionStatus) {

        if (missionStatus.equals(MissionStatus.UNDONE)) {
            return MissionItemStack.getMissionItemStack("UNDONE");
        } else if (missionStatus.equals(MissionStatus.COMPLETED)) {
            return MissionItemStack.getMissionItemStack("COMPLETED");
        } else if (missionStatus.equals(MissionStatus.COLLECTED)) {
            return MissionItemStack.getMissionItemStack("COLLECTED");
        }

        return MissionItemStack.getMissionItemStack("NULL");
    }

    public boolean isMissionDone(MissionItemStack clickedIcon) {
        missionItemStack = clickedIcon;

        return !MissionItemStack.isSameIcon(missionItemStack, MissionItemStack.getMissionItemStack("UNDONE"));
    }

    @Override
    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    @Override
    public void setMissionStatus(MissionStatus missionStatus) {
        this.missionStatus = missionStatus;
    }

    @Override
    public MissionType getMissionType() {
        return this.missionType;
    }

    @Override
    public MissionStatus getMissionStatus() {
        return this.missionStatus;
    }
}
