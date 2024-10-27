package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.tools.NBT;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 21:26
 */
public class MissionItemStack extends ItemStack {
    private static final Map<String, MissionItemStack> nbtGetMissionItemStack = new HashMap<>();
    private static final Map<ItemStack, String> missionItemStackGetNbt = new HashMap<>();
    private static final Map<MissionItemStack, MissionStatus> missionItemStackStatus = new HashMap<>();

    public MissionItemStack(ItemStack icon, String nbtKey, String nbtValue) {
        super(NBT.setCustomNBT(NBT.setCustomNBT(icon, nbtKey, nbtValue), "MissionIcons", "MissionIcons"));
    }

    public MissionItemStack setLore(String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) {
            List<String> loreList = new ArrayList<>(Arrays.asList(lore));
            itemMeta.setLore(loreList);
            this.setItemMeta(itemMeta);
        }
        return this;
    }

    /**
     * 注册物品
     *
     * @param icon 物品
     * @param key  用来对应物品的key
     */
    public static void registerMissionItemStack(MissionItemStack icon, String key) {
        nbtGetMissionItemStack.put(key, icon);
        missionItemStackGetNbt.put(icon, key);
    }

    /**
     * 注册物品所代表的状态
     *
     * @param icon   物品
     * @param status 对应的状态
     */
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

    /**
     * 一体化创建注册 此处创建的物品无法更改状态 是不被Handler认可的
     *
     * @param icon   图标样式
     * @param nbt    给图标添加的nbt标识
     * @param status 图标所代表的状态
     */
    @Deprecated
    public static MissionItemStack toIcon(ItemStack icon, String nbt, MissionStatus status) {
        MissionItemStack mis = toIcon(icon, nbt);
        registerMissionStatus(mis, status);

        return mis;
    }

    @Deprecated
    public static MissionItemStack toIcon(ItemStack icon, String nbt) {
        MissionItemStack mis = new MissionItemStack(icon, nbt, nbt);
        registerMissionItemStack(mis, nbt);

        return mis;
    }

    public static boolean isSameIcon(MissionItemStack fromIcon, MissionItemStack aimIcon) {
        return fromIcon.equals(aimIcon);
    }
}
