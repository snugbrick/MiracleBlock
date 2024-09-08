package github.snugbrick.miracleblock.items.weapon;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.CanInlaid;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemWords;
import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SwordItemStack extends MiracleBlockItemStack implements CanInlaid {
    private int damage;
    private Enchantment enchantment;
    private final InlaidGemItemStack[] inlaidGems;
    private final int slot;
    private final ItemLevel level;
    private final ItemAttribute itemAttribute;
    private ItemWords itemWords;
    private String displayName;
    private double customAttackRange;
    private Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();

    public SwordItemStack(ItemStack item, String key, String value, int inlaidSlot, ItemAttribute itemAttribute, ItemLevel level) {
        super(AboutNBT.setCustomNBT(item, key, value), key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
        slot = inlaidSlot;
        inlaidGems = new InlaidGemItemStack[slot];
        displayName = item.getType().toString();
    }

    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     */
    public SwordItemStack buildItem(SwordItemStack swordItemStack) {
        ItemMeta meta = swordItemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta != null) {
            if (itemAttribute != null) lore.add("物品属性 " + itemAttribute.toString());
            if (level != null) lore.add("物品等级 " + level.toString());
            meta.setLore(lore);
        }
        swordItemStack.setItemMeta(meta);
        return swordItemStack;
    }

    @Override
    public SwordItemStack setInlay(InlaidGemItemStack inlaidGemItemStack, int indexSlot) {
        this.inlaidGems[indexSlot] = inlaidGemItemStack;
        return this;
    }

    /**
     * 设置词条是会同步设置武器名 武器名与setName后的名字相同 所以建议在setName后调用该方法
     * 同时也会给物品添加增益
     *
     * @param itemWords 目标词条
     * @return 本身
     */
    public SwordItemStack setItemWords(ItemWords itemWords) {
        this.itemWords = itemWords;
        ItemMeta meta = this.getItemMeta();
        int level = this.itemWords.getLevel();

        if (meta != null) {
            meta.setDisplayName(this.itemWords.toString() + " " + displayName);

            MiracleBlock.getInstance().getLogger().info("修改了");
            MiracleBlock.getInstance().getLogger().info(String.valueOf(damage));
            MiracleBlock.getInstance().getLogger().info(itemWords.toString());
            MiracleBlock.getInstance().getLogger().info(String.valueOf(customAttackRange));

            //this.modifiers = ArrayListMultimap.create(Objects.requireNonNull(meta.getAttributeModifiers()));
            MiracleBlock.getInstance().getLogger().info(this.modifiers.toString());

            if (level == 0 || level == 1) {
                AttributeModifier aimModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed",
                        -2.4 * 1.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                modifiers.removeAll(Attribute.GENERIC_ATTACK_SPEED);
                modifiers.put(Attribute.GENERIC_ATTACK_SPEED, aimModifier);
                meta.setAttributeModifiers(modifiers);

                this.setCustomAttackRange(4.0);
                if (level == 0) this.setDamage((int) (this.damage * 0.20), true);
                else this.setDamage((int) (this.damage * 0.15), true);
            } else if (level < 6 && 1 < level) {
                AttributeModifier aimModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed",
                        -2.4 * 1.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                modifiers.removeAll(Attribute.GENERIC_ATTACK_SPEED);
                modifiers.put(Attribute.GENERIC_ATTACK_SPEED, aimModifier);
                meta.setAttributeModifiers(modifiers);

                this.setCustomAttackRange(3.0);
                this.setDamage((int) (this.damage * 0.10), true);
            } else if (6 < level && level < 9) {
                AttributeModifier aimModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed",
                        -2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                modifiers.removeAll(Attribute.GENERIC_ATTACK_SPEED);
                modifiers.put(Attribute.GENERIC_ATTACK_SPEED, aimModifier);
                meta.setAttributeModifiers(modifiers);

                this.setCustomAttackRange(3.0);
                this.setDamage((int) (this.damage * 0.05), true);
            } else {
                AttributeModifier aimModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed",
                        -2.4 * 0.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                modifiers.removeAll(Attribute.GENERIC_ATTACK_SPEED);
                modifiers.put(Attribute.GENERIC_ATTACK_SPEED, aimModifier);
                meta.setAttributeModifiers(modifiers);

                this.setCustomAttackRange(2.0);
                this.setDamage((int) (-1 * this.damage * 0.2), true);
            }

            this.setItemMeta(meta);
            MiracleBlock.getInstance().getLogger().info("修改了");
            MiracleBlock.getInstance().getLogger().info(String.valueOf(damage));
            MiracleBlock.getInstance().getLogger().info(itemWords.toString());
            MiracleBlock.getInstance().getLogger().info(String.valueOf(customAttackRange));

        }

        return this;
    }

    public SwordItemStack setDamage(int damage, Boolean isAdd) {
        int copy = this.damage;
        this.damage = damage;

        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            AttributeModifier modifier;
            if (isAdd) {
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage",
                        copy + damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                this.damage = copy;
            } else {
                modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage",
                        damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            }
            modifiers.removeAll(Attribute.GENERIC_ATTACK_DAMAGE);
            modifiers.put(Attribute.GENERIC_ATTACK_DAMAGE, modifier);


            meta.setAttributeModifiers(modifiers);
            this.setItemMeta(meta);
            return this;
        }

        return this;
    }

    public SwordItemStack setName(SwordItemStack swordItemStack, String name) {
        ItemMeta itemMeta = swordItemStack.getItemMeta();
        if (itemMeta != null) itemMeta.setDisplayName(name);
        swordItemStack.setItemMeta(itemMeta);
        displayName = name;

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

    public void setCustomAttackRange(double customAttackRange) {
        this.customAttackRange = customAttackRange;
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

    public double getCustomAttackRange() {
        return customAttackRange;
    }
}
