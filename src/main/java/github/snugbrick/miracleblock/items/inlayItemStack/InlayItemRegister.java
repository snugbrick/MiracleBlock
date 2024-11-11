package github.snugbrick.miracleblock.items.inlayItemStack;

import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InlayItemRegister {
    public void inlayItemRegister() {
        dashItemRegister();
        protectionItemRegister();
        supportItemRegister();
    }

    public void dashItemRegister() {
        MiraBlockItemStack hunting = new InlayGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "hunting",
                ItemAttribute.ITEM_SAYING, ItemLevel.SR, null)
                .buildItemLore()
                .setName("狩猎")
                .setItemModelData(0)
                .setLore(false, "特殊加成=>", "攻击时有5%的概率额外造成一次伤害");

        MiraBlockItemStack go_after = new InlayGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "go_after",
                ItemAttribute.ITEM_SAYING, ItemLevel.SR, null)
                .buildItemLore()
                .setName("追击")
                .setItemModelData(1)
                .setLore(false, "特殊加成=>", "远程武器将有10%的概率多发射一次弹药");

        MiraBlockItemStack blood_thirsty = new InlayGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "blood_thirsty",
                ItemAttribute.ITEM_SAYING, ItemLevel.B, null)
                .buildItemLore()
                .setName("嗜血")
                .setItemModelData(1)
                .setLore(false, "特殊加成=>", "让敌人持续流血");

        MiraBlockItemStack assassination = new InlayGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "assassination",
                ItemAttribute.ITEM_SAYING, ItemLevel.A, null)
                .buildItemLore()
                .setName("暗杀")
                .setItemModelData(1)
                .setLore(false, "特殊加成=>", "命中目标将有30%的概率触发尖刺");

        MiraBlockItemStack.register(assassination, "assassination");
        MiraBlockItemStack.register(blood_thirsty, "blood_thirsty");
        MiraBlockItemStack.register(go_after, "go_after");
        MiraBlockItemStack.register(hunting, "hunting");
    }

    public void protectionItemRegister() {
        MiraBlockItemStack rebound = new InlayGemItemStack(new ItemStack(Material.EMERALD), "miracle_inlay", "rebound",
                ItemAttribute.ITEM_SAYING, ItemLevel.B, null)
                .buildItemLore()
                .setName("反弹")
                .setItemModelData(1)
                .setLore(false, "特殊加成=>", "有28%的概率击飞目标");

        MiraBlockItemStack.register(rebound, "rebound");
    }

    public void supportItemRegister() {

    }
}