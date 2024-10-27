package github.snugbrick.miracleblock.items.armor;

import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import org.bukkit.inventory.ItemStack;

public class ArmorItemStack extends MiraBlockItemStack {
    private double armor_prot;
    private double armor_toughness;
    private int promote_health;
    private ItemAttribute itemAttribute;
    private ItemLevel itemLevel;

    public ArmorItemStack(ItemStack item, String key, String value, ItemAttribute itemAttribute, ItemLevel itemLevel) {
        super(item, key, value);
        this.itemAttribute = itemAttribute;
        this.itemLevel = itemLevel;
    }

    public ArmorItemStack(MiraBlockItemStack miracleBlockItemStack) {
        super(miracleBlockItemStack);
    }

    public ArmorItemStack setArmor_prot(double armor_prot) {
        this.armor_prot = armor_prot;
        return this;
    }

    public ArmorItemStack setArmor_toughness(double armor_toughness) {
        this.armor_toughness = armor_toughness;
        return this;
    }

    public ArmorItemStack setPromote_health(int promote_health) {
        this.promote_health = promote_health;
        return this;
    }


    public double getArmor_prot() {
        return armor_prot;
    }

    public double getArmor_toughness() {
        return armor_toughness;
    }

    public int getPromote_health() {
        return promote_health;
    }

    public ItemAttribute getItemAttribute() {
        return itemAttribute;
    }

    public ItemLevel getItemLevel() {
        return itemLevel;
    }
}
