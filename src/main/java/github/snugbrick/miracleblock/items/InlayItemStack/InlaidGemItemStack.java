package github.snugbrick.miracleblock.items.InlayItemStack;

import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MainItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InlaidGemItemStack extends MainItemStack {
    private ItemLevel level;
    private ItemAttribute itemAttribute;

    public InlaidGemItemStack(ItemStack item, String key, String value, ItemAttribute itemAttribute, ItemLevel level) {
        super(item, key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
    }

    public InlaidGemItemStack(MainItemStack item) {
        super(item);
    }

    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     *
     * @return 返回MiracleBlockItemStack 是这个类的终结链式方法
     */
    public MainItemStack buildItemLore() {
        if (itemAttribute != null) {
            this.setLore(false, "<=======物品属性=======>");
            this.setLore(false, this.itemAttribute.toString());
        }
        if (level != null) {
            this.setLore(false, "<=======物品等级=======>");
            this.setLore(false, this.level.toString());
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

