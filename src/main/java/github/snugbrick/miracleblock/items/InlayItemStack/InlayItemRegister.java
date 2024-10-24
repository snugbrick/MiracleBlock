package github.snugbrick.miracleblock.items.InlayItemStack;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.MainItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InlayItemRegister {
    public void inlayItemRegister() {
        dashItemRegister();
        protectionItemRegister();
        supportItemRegister();
    }

    public void dashItemRegister() {
        MainItemStack hunting = new InlaidGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "hunting",
                ItemAttribute.ITEM_SAYING, ItemLevel.SR)
                .setName("狩猎")
                .buildItemLore()
                .setItemModelData(0)
                .setLore("特殊加成=>", "攻击时有5%的概率额外造成一次伤害");

        MainItemStack go_after = new InlaidGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "go_after",
                ItemAttribute.ITEM_SAYING, ItemLevel.SR)
                .setName("追击")
                .buildItemLore()
                .setItemModelData(1)
                .setLore("特殊加成=>", "远程武器将有10%的概率多发射一次弹药");

        MainItemStack blood_thirsty = new InlaidGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "blood_thirsty",
                ItemAttribute.ITEM_SAYING, ItemLevel.B)
                .setName("嗜血")
                .buildItemLore()
                .setItemModelData(1)
                .setLore("特殊加成=>", "让敌人持续流血");

        MainItemStack assassination = new InlaidGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "assassination",
                ItemAttribute.ITEM_SAYING, ItemLevel.A)
                .setName("暗杀")
                .buildItemLore()
                .setItemModelData(1)
                .setLore("特殊加成=>", "命中目标将有30%的概率触发尖刺");

        MainItemStack.register(assassination, "assassination");
        MainItemStack.register(blood_thirsty, "blood_thirsty");
        MainItemStack.register(go_after, "go_after");
        MainItemStack.register(hunting, "hunting");
    }

    public void protectionItemRegister() {
        MainItemStack rebound = new InlaidGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "rebound",
                ItemAttribute.ITEM_SAYING, ItemLevel.B)
                .setName("反弹")
                .buildItemLore()
                .setItemModelData(1)
                .setLore("特殊加成=>", "有28%的概率击飞目标");

        MainItemStack.register(rebound, "rebound");
    }

    public void supportItemRegister() {

    }
}