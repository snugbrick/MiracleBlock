package github.snugbrick.miracleblock.items.InlayItemStack;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InlayItemRegister {
    public void inlayItemRegister() {
        MiracleBlockItemStack hunting = new InlaidGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "hunting",
                ItemAttribute.ITEM_SAYING, ItemLevel.B)
                .setName("狩猎")
                .buildItemLore()
                .setItemModelData(0)
                .setLore("特殊加成=>", "攻击时有5%的概率额外造成一次伤害");
        MiracleBlockItemStack.register(hunting, "hunting");
    }
}