package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SwordRegister {
    public void swordRegister() {
        SwordItemStack dullSword = new SwordItemStack(new ItemStack(Material.WOODEN_SWORD)
                , "miracle_sword", "dull_sword", 1, ItemAttribute.WOOD, ItemLevel.C)
                .setDamage(10)
                .setName("钝剑")
                .setLore("\"这可是我自己亲手打造的\"", "--吉米如是说")
                .setItemModelData(0)
                .addGain()
                .buildItemLore();
        MiracleBlockItemStack.register(dullSword, "dull_sword");
    }
}
