package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemWords;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwordItemStack extends MiracleBlockItemStack implements SwordInlaid {
    private int damage;
    private Enchantment enchantment;
    private final InlaidGemItemStack[] inlaidGems;
    private final int slot;
    private final ItemLevel level;
    private final ItemAttribute itemAttribute;
    private ItemWords itemWords;

    public SwordItemStack(ItemStack item, String key, String value, int inlaidSlot, ItemAttribute itemAttribute, ItemLevel level) {
        super(item, key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
        slot = inlaidSlot;
        inlaidGems = new InlaidGemItemStack[slot];
    }

    @Override
    public SwordItemStack setInlay(InlaidGemItemStack inlaidGemItemStack, int indexSlot) {
        this.inlaidGems[indexSlot] = inlaidGemItemStack;
        return this;
    }

    public SwordItemStack setItemWords(ItemWords itemWords) {
        this.itemWords = itemWords;
        return this;
    }

    public SwordItemStack setName(SwordItemStack swordItemStack, String name) {
        ItemMeta itemMeta = swordItemStack.getItemMeta();
        if (itemMeta != null) itemMeta.setDisplayName(name);
        swordItemStack.setItemMeta(itemMeta);

        return swordItemStack;
    }

    public SwordItemStack setLore(SwordItemStack swordItemStack, String... lore) {
        ItemMeta itemMeta = swordItemStack.getItemMeta();
        List<String> allLore;
        if (itemMeta != null) {
            allLore = new ArrayList<>(Arrays.asList(lore));
            itemMeta.setLore(allLore);
        }
        swordItemStack.setItemMeta(itemMeta);

        return swordItemStack;
    }

    public SwordItemStack setEnchantments(Enchantment enchantments) {
        this.enchantment = enchantments;
        return this;
    }

    public SwordItemStack setDamage(int damage) {
        this.damage = damage;
        return this;
    }


    public int getDamage() {
        return damage;
    }

    public int getSlotCount() {
        return slot;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public InlaidGemItemStack[] getInlaidGemItemStack() {
        return inlaidGems;
    }

    public ItemLevel getLevel() {
        return level;
    }

    public ItemAttribute getItemAttribute() {
        return itemAttribute;
    }

    public ItemWords getItemWords() {
        return itemWords;
    }
}
