package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.weapon.ItemWords;
import github.snugbrick.miracleblock.items.weapon.SwordItemStack;
import github.snugbrick.miracleblock.tools.AboutNameSpacedKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MiracleBlockItemStack extends ItemStack {
    private static final Map<String, MiracleBlockItemStack> allMiracleBlockItemStack = new HashMap<>();


    public MiracleBlockItemStack(ItemStack item, String key, String value) {
        super(AboutNameSpacedKey.setNameSpacedKey(item, new NamespacedKey(MiracleBlock.getInstance(), key), value));
    }

    public MiracleBlockItemStack(ItemStack item) {
        super(item);
    }

    public static void register(MiracleBlockItemStack aimItem, String key) {
        allMiracleBlockItemStack.put(key, aimItem);
    }

    public static MiracleBlockItemStack getItem(String key) {
        MiracleBlockItemStack miracleItem = allMiracleBlockItemStack.get(key);
        ItemMeta miracleItemMeta = miracleItem.getItemMeta();
        PersistentDataContainer container = Objects.requireNonNull(miracleItemMeta).getPersistentDataContainer();

        if (container.has(new NamespacedKey(MiracleBlock.getInstance(), "miracle_sword"), PersistentDataType.STRING)) {
            SwordItemStack swordItemStack = (SwordItemStack) miracleItem;
            return swordItemStack.setItemWords(ItemWords.getRandomItemWords(swordItemStack)).addGain();
        }
        return miracleItem;
    }

    public static Map<String, MiracleBlockItemStack> getAllMiracleBlockItemStack() {
        return allMiracleBlockItemStack;
    }
}
