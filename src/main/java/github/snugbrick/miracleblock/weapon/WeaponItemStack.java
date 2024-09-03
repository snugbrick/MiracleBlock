package github.snugbrick.miracleblock.weapon;

import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WeaponItemStack extends ItemStack {
    private static Map<String, WeaponItemStack> allWeapons = new HashMap<>();

    public WeaponItemStack(ItemStack item, String nbtKey, String nbtValue) {
        super(AboutNBT.setCustomNBT(item, nbtKey, nbtValue));
    }

    public void registerWeaponItemStack(WeaponItemStack aimItem, String key) {
        allWeapons.put(key, aimItem);
    }

    public WeaponItemStack getWeaponItemStack(String key) {
        return allWeapons.get(key);
    }
}
