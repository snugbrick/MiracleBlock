package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.tools.NSK;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.*;

public class MainItemStack extends ItemStack {
    private static final Map<String, MainItemStack> allMiracleBlockItemStack = new HashMap<>();
    private int model;

    public MainItemStack(ItemStack item, String key, String value) {
        super(NSK.setNameSpacedKey(item, new NamespacedKey(MiracleBlock.getInstance(), key), value));
    }

    public MainItemStack(ItemStack item) {
        super(item);
    }

    /**
     * 设置物品简介 第一个参数设置是否覆盖
     *
     * @param lore 内容
     */
    public MainItemStack setLore(Boolean isCover, String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null && !isCover) {
            List<String> allLore = new ArrayList<>(Arrays.asList(lore));
            List<String> originLore = itemMeta.getLore();
            if (originLore != null) {
                originLore.addAll(allLore);
                itemMeta.setLore(originLore);
            } else {
                itemMeta.setLore(allLore);
            }
        } else {
            if (itemMeta != null)
                itemMeta.setLore(Arrays.asList(lore));
        }
        this.setItemMeta(itemMeta);
        return this;
    }

    public MainItemStack setName(String name) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
            this.setItemMeta(itemMeta);

            return this;
        }
        return null;
    }

    /**
     * 设置物品模型
     *
     * @param num 序号
     */
    public MainItemStack setItemModelData(int num) {
        this.model = num;
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) itemMeta.setCustomModelData(num);
        this.setItemMeta(itemMeta);
        return this;
    }

    @Nonnull
    @Override
    public MainItemStack clone() {
        MainItemStack aimItem = (MainItemStack) super.clone();
        aimItem.setItemModelData(this.model);
        return aimItem;
    }

    public static void register(MainItemStack aimItem, String key) {
        allMiracleBlockItemStack.put(key, aimItem);
    }

    public void register(String key) {
        allMiracleBlockItemStack.put(key, this);
    }

    public static MainItemStack getItem(String key) {
        if (key == null) return null;
        return allMiracleBlockItemStack.get(key) == null ? null : allMiracleBlockItemStack.get(key);
    }

    public static Map<String, MainItemStack> getAllMiracleBlockItemStack() {
        return allMiracleBlockItemStack;
    }
}
