package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.MainItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SwordRegister {
    public void swordRegister() {
        MainItemStack dullSword = new SwordItemStack(new ItemStack(Material.WOODEN_SWORD)
                , "miracle_sword", "dull_sword", 1, ItemAttribute.WOOD, ItemLevel.C)
                .setDamage(10)
                .buildSword()
                .setName("钝剑")
                .setLore("\"这可是我自己亲手打造的\"", "--吉米如是说")
                //.addGain()
                .setItemModelData(0);
        MainItemStack.register(dullSword, "dull_sword");
    }
}
