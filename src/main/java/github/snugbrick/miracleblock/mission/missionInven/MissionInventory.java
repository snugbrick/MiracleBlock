package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.mission.MissionStatusHandler;
import github.snugbrick.miracleblock.mission.PlayersMissionStatus;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 22:33
 */
public class MissionInventory {
    private final Inventory playerInventory;

    public MissionInventory(Player player) {
        this(player, 54, "Mission Menu");
    }

    public MissionInventory(Player player, int size, String title) {
        playerInventory = Bukkit.createInventory(player, size, title);
    }

    public void openMissionInventory(Player player) throws SQLException {
        List<String> missions = LoadLangFiles.getMessageList("MissionName");
        List<MissionItemStack> missionItemStack = new ArrayList<>();

        for (String mission_name : missions) {
            if (PlayersMissionStatus.isUNDONE(player, mission_name)) {
                MissionItemStack icon = MissionStatusHandler.getMissionIcon(MissionStatus.UNDONE);
                ItemMeta item_meta = icon.getItemMeta();
                item_meta.setDisplayName(mission_name);
                icon.setItemMeta(item_meta);
                missionItemStack.add(icon);

                //MiracleBlock.getInstance().getLogger().info(mission_name + "未完");
            } else if (PlayersMissionStatus.isCOMPLETED(player, mission_name)) {
                MissionItemStack icon = MissionStatusHandler.getMissionIcon(MissionStatus.COMPLETED);
                ItemMeta item_meta = icon.getItemMeta();
                item_meta.setDisplayName(mission_name);
                icon.setItemMeta(item_meta);
                missionItemStack.add(icon);

                //MiracleBlock.getInstance().getLogger().info(mission_name + "已完");
            } else if (PlayersMissionStatus.isCOLLECTED(player, mission_name)) {
                MissionItemStack icon = MissionStatusHandler.getMissionIcon(MissionStatus.COLLECTED);
                ItemMeta item_meta = icon.getItemMeta();
                item_meta.setDisplayName(mission_name);
                icon.setItemMeta(item_meta);
                missionItemStack.add(icon);

                //MiracleBlock.getInstance().getLogger().info(mission_name + "已收");
            }
        }

        int index = 0;
        for (MissionItemStack icon : missionItemStack) {
            addInventoryItem(icon, index);
            index++;
        }
        player.openInventory(playerInventory);
    }

    /**
     * 通过索引获取指定位置的任务物品
     *
     * @param index 索引
     * @return 位置上的任务物品
     */
    public MissionItemStack getInventoryMissionItemStack(int index) {
        MissionStatus missionStatus = MissionStatusHandler.getItemStackStatus(playerInventory.getItem(index));
        MissionItemStack indexItem = MissionStatusHandler.getMissionIcon(missionStatus);

        return indexItem;
    }

    /**
     * 给指定索引上添加或替换物品
     *
     * @param item  指定的待替换物品
     * @param index 索引
     */
    public void addInventoryItem(MissionItemStack item, int index) {
        playerInventory.setItem(index, item);
    }
}
