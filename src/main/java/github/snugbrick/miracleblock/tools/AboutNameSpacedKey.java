package github.snugbrick.miracleblock.tools;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AboutNameSpacedKey {
    /**
     * 设置物品的NameSpacedKey
     *
     * @param item  目标物品
     * @param key   待设置NameSpacedKey
     * @param value NameSpacedKey所对应的值
     * @return 设置完成的物品
     */
    public static ItemStack setNameSpacedKey(ItemStack item, NamespacedKey key, String value) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(key, PersistentDataType.STRING, value);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

    /**
     * 检查键是否存在
     *
     * @param item          被检查物品
     * @param key           被检查NameSpacedKey
     * @return boolean
     */
    public static boolean hasNameSpacedKey(ItemStack item, NamespacedKey key) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                return container.has(key, PersistentDataType.STRING);
            }
        }
        return false;
    }

    /**
     * 获得指定NameSpaceKey的值
     *
     * @param item 目标物品
     * @param key  目标NameSpacedKey
     * @return 被获取的值
     */
    public static String getNameSpacedKey(ItemStack item, NamespacedKey key) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                // 获取 NamespacedKey 对应的值
                return container.get(key, PersistentDataType.STRING);
            }
        }
        return null;
    }
}
