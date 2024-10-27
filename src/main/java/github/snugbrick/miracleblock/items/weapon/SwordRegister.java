package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MainItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SwordRegister {
    public void swordRegister() {
        MainItemStack dullSword = new SwordItemStack(new ItemStack(Material.WOODEN_SWORD)
                , "miracle_sword", "dull_sword", 0, ItemAttribute.WOOD, ItemLevel.C, WeaponItemWords.COMMON)
                .setDamage(10)
                .buildSword()
                .setName("钝剑")
                .setLore(false, "\"这可是我自己亲手打造的\"", "--吉米如是说")
                .setItemModelData(0);
        MainItemStack.register(dullSword, "dull_sword");
    }
}
