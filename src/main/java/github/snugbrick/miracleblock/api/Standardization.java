package github.snugbrick.miracleblock.api;

import github.snugbrick.miracleblock.tools.NBT;
import org.bukkit.inventory.ItemStack;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.28 21:56
 */
@Deprecated
public class Standardization {
    /**
     * 标准化物品 已弃用
     */
    public static ItemStack missionStandardization(ItemStack itemStack, String nbtKey, String nbtValue) {
        ItemStack sendItem = NBT.setNBT(itemStack, nbtKey, nbtValue);
        return NBT.setNBT(sendItem, "MissionIcons", "MissionIcons");
    }
}
