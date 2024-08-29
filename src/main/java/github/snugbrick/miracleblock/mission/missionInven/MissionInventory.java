package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.mission.MissionStatusHandler;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 22:33
 */
public class MissionInventory {

    private List<String> missionName = new LoadLangFiles().getMessageList("MissionName");

    private static final Inventory customInventory = Bukkit.createInventory(null, 54, "Mission Menu");

    public void openMissionInventory(Player player) {
        MissionItemStack wakeUpMissionItem = iconDisplayName(MissionItemStack.getMissionItemStack("UNDONE"), missionName.get(0));
        addInventoryItem(wakeUpMissionItem, 0);

        player.openInventory(customInventory);
    }

    public MissionItemStack getInventoryMissionItemStack(int index) {
        MissionStatus missionStatus = MissionStatusHandler.getItemStackStatus(customInventory.getItem(index));
        MissionItemStack indexItem = MissionStatusHandler.getMissionIcon(missionStatus);

        return indexItem;
    }

    public static void addInventoryItem(MissionItemStack item, int index) {
        customInventory.setItem(index, item);
    }

    private MissionItemStack iconDisplayName(MissionItemStack icon, String name) {
        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setDisplayName(name);
        icon.setItemMeta(itemMeta);

        return icon;
    }
}
