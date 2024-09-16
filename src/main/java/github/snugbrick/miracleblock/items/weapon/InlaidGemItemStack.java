package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.inventory.ItemStack;

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

    public String getDisplayName() {
        return displayName;
    }

    public ItemAttribute getItemAttribute() {
        return itemAttribute;
    }

    public ItemLevel getLevel() {
        return level;
    }
}

