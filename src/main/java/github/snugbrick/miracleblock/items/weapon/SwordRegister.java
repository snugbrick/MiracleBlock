package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SwordRegister {
    public void swordRegister() {
        MiraBlockItemStack dullSword = new SwordItemStack(new ItemStack(Material.WOODEN_SWORD)
                , "miracle_sword", "dull_sword", 1, ItemAttribute.WOOD, ItemLevel.C, WeaponItemWords.COMMON)
                .setDamage(10)
                .setAttackSpeed(1.0)
                .setCustomAttackRange(3.0)
                .buildSword()
                .setName("钝剑")
                .setLore(false, "\"这可是我自己亲手打造的\"", "--吉米如是说")
                .setItemModelData(0);
        MiraBlockItemStack.register(dullSword, "dull_sword");
    }
}
