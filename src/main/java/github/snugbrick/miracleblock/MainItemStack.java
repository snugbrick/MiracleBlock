package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.items.weapon.SwordItemStack;
import github.snugbrick.miracleblock.items.weapon.WeaponItemWords;
import github.snugbrick.miracleblock.tools.AboutNameSpacedKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.*;

public class MainItemStack extends ItemStack {
    private static final Map<String, MainItemStack> allMiracleBlockItemStack = new HashMap<>();
    private int model;

    public MainItemStack(ItemStack item, String key, String value) {
        super(AboutNameSpacedKey.setNameSpacedKey(item, new NamespacedKey(MiracleBlock.getInstance(), key), value));
    }

    public MainItemStack(ItemStack item) {
        super(item);
    }

    /**
     * 设置物品简介
     *
     * @param lore 内容
     */
    public MainItemStack setLore(String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        List<String> allLore;
        if (itemMeta != null) {
            allLore = new ArrayList<>(Arrays.asList(lore));
            itemMeta.setLore(allLore);
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
        MainItemStack miracleItem = allMiracleBlockItemStack.get(key);
        ItemMeta miracleItemMeta = miracleItem.getItemMeta();
        PersistentDataContainer container = Objects.requireNonNull(miracleItemMeta).getPersistentDataContainer();

        if (container.has(new NamespacedKey(MiracleBlock.getInstance(), "miracle_sword"), PersistentDataType.STRING)) {
            SwordItemStack swordItemStack = (SwordItemStack) miracleItem;
            return swordItemStack.setItemWords(WeaponItemWords.getRandomItemWords(swordItemStack)).addGain();
        }
        return miracleItem;
    }

    public static Map<String, MainItemStack> getAllMiracleBlockItemStack() {
        return allMiracleBlockItemStack;
    }
}
