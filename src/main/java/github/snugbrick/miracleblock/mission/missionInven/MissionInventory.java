package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.ConfigGetter;
import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.mission.MissionStatusHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 22:33
 */
public class MissionInventory {

    private static final Inventory customInventory = Bukkit.createInventory(null, 54, "Mission Menu");

    public void openMissionInventory(Player player) {
        initInventory(27);
        player.openInventory(customInventory);
    }

    /**
     * 通过索引获取指定位置的任务物品
     * @param index 索引
     * @return 位置上的任务物品
     */
    public MissionItemStack getInventoryMissionItemStack(int index) {
        MissionStatus missionStatus = MissionStatusHandler.getItemStackStatus(customInventory.getItem(index));
        MissionItemStack indexItem = MissionStatusHandler.getMissionIcon(missionStatus);

        return indexItem;
    }

    /**
     * 给指定索引上添加或替换物品
     * @param item 指定的待替换物品
     * @param index 索引
     */
    public static void addInventoryItem(MissionItemStack item, int index) {
        customInventory.setItem(index, item);
    }

    private void initInventory(int missionNum) {
        for (int l = 0; l < missionNum; l++) {
            MissionItemStack firMission = iconDisplayName(MissionItemStack.getMissionItemStack("UNDONE"), ConfigGetter.missionName.get(l));
            addInventoryItem(firMission, l);
        }
    }

    private MissionItemStack iconDisplayName(MissionItemStack icon, String name) {
        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setDisplayName(name);
        icon.setItemMeta(itemMeta);

        return icon;
    }
}
