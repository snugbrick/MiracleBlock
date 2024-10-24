package github.snugbrick.miracleblock.items.InlayItemStack;

import github.snugbrick.miracleblock.MainItemStack;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InlaidGemItemStack extends MainItemStack {
    private ItemLevel level;
    private ItemAttribute itemAttribute;

    public InlaidGemItemStack(ItemStack item, String key, String value, ItemAttribute itemAttribute, ItemLevel level) {
        super(AboutNBT.setCustomNBT(item, key, value), key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
    }

    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     *
     * @return 返回MiracleBlockItemStack 是这个类的终结链式方法
     */
    public MainItemStack buildItemLore() {
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
        return this.getDisplayName();
    }

    public String getDisplayName() {
        return Objects.requireNonNull(this.getItemMeta()).getDisplayName();
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

