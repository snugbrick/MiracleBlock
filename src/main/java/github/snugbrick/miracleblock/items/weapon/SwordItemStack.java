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
import github.snugbrick.miracleblock.tools.AboutNameSpacedKey;
import org.bukkit.NamespacedKey;
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

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    private double attackSpeed;

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
            if (itemAttribute != null) {
                lore.add("+=====物品属性=====+");
                lore.add(itemAttribute.toString());
            }
            if (level != null) {
                lore.add("+=====物品等级=====+");
                lore.add(level.toString());
            }
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

    public SwordItemStack setItemWords(ItemWords itemWords) {
        this.itemWords = itemWords;
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(this.itemWords.toString() + " " + displayName);
            if (this.itemWords.getLevel() < 6) {
                this.setDamage(20);
                this.setAttackSpeed(0.8);
                this.setCustomAttackRange(4.0);
            } else {
                this.setDamage(10);
                this.setAttackSpeed(1);
                this.setCustomAttackRange(2.0);
            }
            this.setItemMeta(meta);
        }
        AboutNameSpacedKey.setNameSpacedKey(this, new NamespacedKey(MiracleBlock.getInstance(), "range"), String.valueOf(this.getCustomAttackRange()));
        AboutNameSpacedKey.setNameSpacedKey(this, new NamespacedKey(MiracleBlock.getInstance(), "damage"), String.valueOf(this.getDamage()));
        return this;
    }

    public SwordItemStack setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public static SwordItemStack addAttribute(SwordItemStack swordItemStack) {
        ItemMeta itemMeta = swordItemStack.getItemMeta();
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        if (itemMeta != null) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                itemMeta.removeAttributeModifier(equipmentSlot);
            }
            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage",
                    swordItemStack.getDamage(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            AttributeModifier speedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed",
                    -2.4 * swordItemStack.getAttackSpeed(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            modifiers.put(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            modifiers.put(Attribute.GENERIC_ATTACK_SPEED, speedModifier);

            itemMeta.setAttributeModifiers(modifiers);
            swordItemStack.setItemMeta(itemMeta);
        }
        MiracleBlock.getInstance().getLogger().info("修改了");
        MiracleBlock.getInstance().getLogger().info(String.valueOf(swordItemStack.getDamage()));
        MiracleBlock.getInstance().getLogger().info(swordItemStack.getItemWords().toString());
        MiracleBlock.getInstance().getLogger().info(String.valueOf(swordItemStack.getCustomAttackRange()));
        //this.modifiers = ArrayListMultimap.create(Objects.requireNonNull(meta.getAttributeModifiers()));
        MiracleBlock.getInstance().getLogger().info(modifiers.toString());

        return swordItemStack;
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
