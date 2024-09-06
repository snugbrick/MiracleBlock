package github.snugbrick.miracleblock.mission;

import github.snugbrick.miracleblock.mission.missionInven.MissionItemStack;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.28 18:14
 */
public class MissionStatusHandler extends Mission {

    private MissionType missionType;

    private MissionStatus missionStatus;

    private MissionItemStack missionItemStack;

    /**
     * 从一个mission图标获取其中的状态
     *
     * @param itemStack 待检测图标
     * @return 此图标的状态
     */
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

    /**
     * 通过状态来获取图标
     *
     * @param missionStatus 对应状态
     * @return 图标物品
     */
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

    /**
     * 判断任务是否完成
     *
     * @param clickedIcon 图标物品
     * @return 是否完成
     */
    public boolean isMissionDone(Player player, MissionItemStack clickedIcon) throws SQLException {
        missionItemStack = clickedIcon;
        MissionStatus status = PlayersMissionStatus.getPlayerMissionStatus(player, missionItemStack.getItemMeta().getDisplayName());

        return status.equals(MissionStatus.COMPLETED);
    }

    public boolean isMissionCollected(Player player, MissionItemStack clickedIcon) throws SQLException {
        missionItemStack = clickedIcon;
        MissionStatus status = PlayersMissionStatus.getPlayerMissionStatus(player, missionItemStack.getItemMeta().getDisplayName());

        return status.equals(MissionStatus.COLLECTED);
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
