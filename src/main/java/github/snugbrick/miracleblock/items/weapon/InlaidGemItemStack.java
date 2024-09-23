package github.snugbrick.miracleblock.items.weapon;

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
    private int modelNum;

    public InlaidGemItemStack(ItemStack item, String key, String value, ItemAttribute itemAttribute, ItemLevel level) {
        super(AboutNBT.setCustomNBT(item, key, value), key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
        displayName = item.getType().toString();
    }

    public InlaidGemItemStack setLevel(ItemLevel level) {
        this.level = level;
        return this;
    }

    public InlaidGemItemStack setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public InlaidGemItemStack setItemAttribute(ItemAttribute itemAttribute) {
        this.itemAttribute = itemAttribute;
        return this;
    }

    public InlaidGemItemStack setItemModelData(int num) {
        this.modelNum = num;
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) itemMeta.setCustomModelData(num);
        this.setItemMeta(itemMeta);
        return this;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getModelNum() {
        return modelNum;
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

