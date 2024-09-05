package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.tools.AboutNameSpacedKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MiracleBlockItemStack extends ItemStack {
    private static final Map<String, MiracleBlockItemStack> allMiracleBlockItemStack = new HashMap<>();


    public MiracleBlockItemStack(ItemStack item, String key, String value) {
        super(AboutNameSpacedKey.setNameSpacedKey(item, new NamespacedKey(MiracleBlock.getInstance(), key), value));
    }

    public static void registerMiracleBlockItemStack(MiracleBlockItemStack aimItem, String key) {
        allMiracleBlockItemStack.put(key, aimItem);
    }

    public static MiracleBlockItemStack getMiracleBlockItemStack(String key) {
        return allMiracleBlockItemStack.get(key);
    }
}
