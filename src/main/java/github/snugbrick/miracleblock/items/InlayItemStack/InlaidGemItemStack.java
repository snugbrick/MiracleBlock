package github.snugbrick.miracleblock.items.InlayItemStack;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InlaidGemItemStack extends MiracleBlockItemStack {
    private ItemLevel level;
    private ItemAttribute itemAttribute;
    private String displayName;

    public InlaidGemItemStack(ItemStack item, String key, String value, ItemAttribute itemAttribute, ItemLevel level) {
        super(AboutNBT.setCustomNBT(item, key, value), key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
        displayName = item.getType().toString();
    }

    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     *
     * @return 返回MiracleBlockItemStack 是这个类的终结链式方法
     */
    public MiracleBlockItemStack buildItemLore() {
        if (itemAttribute != null) {
            this.setLore("<=======物品属性=======>");
            this.setLore(this.itemAttribute.toString());
        }
        if (level != null) {
            this.setLore("<=======物品等级=======>");
            this.setLore(this.level.toString());
        }
        return this;
    }

    public InlaidGemItemStack setName(String displayName) {
        this.displayName = displayName;
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) itemMeta.setDisplayName(displayName);
        this.setItemMeta(itemMeta);
        return this;
    }

    public InlaidGemItemStack setLevel(ItemLevel level) {
        this.level = level;
        return this;
    }

    public InlaidGemItemStack setItemAttribute(ItemAttribute itemAttribute) {
        this.itemAttribute = itemAttribute;
        return this;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemAttribute getItemAttribute() {
        return itemAttribute;
    }

    public ItemLevel getLevel() {
        return level;
    }

    public static class GainPackage {
        GainPackage() {

        }
    }
}

