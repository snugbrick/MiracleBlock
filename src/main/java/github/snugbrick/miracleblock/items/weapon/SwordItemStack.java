package github.snugbrick.miracleblock.items.weapon;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.CanInlaid;
import github.snugbrick.miracleblock.items.InlayItemStack.InlaidGemItemStack;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemAdditional.ItemLevel;
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

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SwordItemStack extends MiracleBlockItemStack implements CanInlaid {
    private double damage = 0;
    private Enchantment enchantment;
    private InlaidGemItemStack[] inlaidGems;
    private int slot;
    private ItemLevel level;
    private ItemAttribute itemAttribute;
    private WeaponItemWords itemWords;
    private String displayName;
    private double customAttackRange;
    private double attackSpeed = 1;

    public SwordItemStack(ItemStack item, String key, String value, int inlaidSlot, ItemAttribute itemAttribute, ItemLevel level) {
        super(AboutNBT.setCustomNBT(item, key, value), key, value);
        this.level = level;
        this.itemAttribute = itemAttribute;
        slot = inlaidSlot;
        inlaidGems = new InlaidGemItemStack[slot];
        displayName = item.getType().toString();
    }

    public SwordItemStack(MiracleBlockItemStack miracleBlockItemStack) {
        super(miracleBlockItemStack);
    }

    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     *
     * @return 返回MiracleBlockItemStack 是这个类的终结链式方法
     */
    public MiracleBlockItemStack buildItemLore() {
        ItemMeta meta = this.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta != null) {
            if (itemAttribute != null) {
                lore.add("<=======物品属性=======>");
                lore.add(itemAttribute.toString());
            }
            if (level != null) {
                lore.add("<=======物品等级=======>");
                lore.add(level.toString());
            }
            if (customAttackRange != 0) {
                lore.add("<=======攻击距离=======>");
                lore.add(String.valueOf(customAttackRange));
            }
            if (slot != 0) {
                lore.add("<========" + slot + "个槽位========>");
                String result = IntStream.range(0, slot)
                        .mapToObj(i ->
                                i < inlaidGems.length ? "[" /* +(inlaidGems[i] == null ? "" : inlaidGems[i].toString())*/ + "]" : "[ ]")
                        .collect(Collectors.joining("  "));
                lore.add(result);
            }
            meta.setLore(lore);
        }
        this.setItemMeta(meta);

        return this;
    }

    /**
     * 添加属性 将词条 属性加成赋予物品 建议倒数第二个调用
     */
    public SwordItemStack addGain() {
        ItemMeta itemMeta = this.getItemMeta();
        ItemAttribute.GainPackage gainPackage = this.getItemAttribute().getGain(this.getItemAttribute().getAttribute());
        if (gainPackage != null) {
            this.damage *= gainPackage.getGainDamage();
            this.attackSpeed *= gainPackage.getGainAttackSpeed();
        }
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        if (itemMeta != null) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                itemMeta.removeAttributeModifier(equipmentSlot);
            }
            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage",
                    this.getDamage(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            AttributeModifier speedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed",
                    -2.4 * this.getAttackSpeed(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
            modifiers.put(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
            modifiers.put(Attribute.GENERIC_ATTACK_SPEED, speedModifier);

            itemMeta.setAttributeModifiers(modifiers);
            this.setItemMeta(itemMeta);
        }
        AboutNameSpacedKey.setNameSpacedKey(this,
                new NamespacedKey(MiracleBlock.getInstance(), "range"), String.valueOf(this.getCustomAttackRange()));
        AboutNameSpacedKey.setNameSpacedKey(this,
                new NamespacedKey(MiracleBlock.getInstance(), "damage"), String.valueOf(this.getDamage()));
        AboutNameSpacedKey.setNameSpacedKey(this,
                new NamespacedKey(MiracleBlock.getInstance(), "attribute"), this.getItemAttribute().toString());
        return this;
    }

    public SwordItemStack setItemWords(WeaponItemWords itemWords) {
        this.itemWords = itemWords;
        WeaponItemWords.GainPackage gain = itemWords.getGain(itemWords.getLevel());
        ItemMeta meta = this.getItemMeta();

        if (meta != null && gain != null) {
            meta.setDisplayName(this.itemWords.toString() + " " + displayName);
            this.setDamage(this.damage *= gain.getGainDamage());
            this.setAttackSpeed(this.attackSpeed *= gain.getGainAttackSpeed());
            this.setCustomAttackRange(this.customAttackRange += gain.getGainReach());

            this.setItemMeta(meta);
        }
        return this;
    }

    @Nonnull
    @Override
    public SwordItemStack clone() {
        SwordItemStack swordItemStack = (SwordItemStack) super.clone();
        swordItemStack.setItemWords(this.getItemWords())
                .setDamage(this.getDamage())
                .setAttackSpeed(this.getAttackSpeed())
                .setInlay(Arrays.stream(this.getInlaidGemItemStack()).iterator())
                .setName(Objects.requireNonNull(this.getItemMeta()).getDisplayName())
                .setCustomAttackRange(this.getCustomAttackRange())
                .setEnchantments(this.getEnchantment())
                .setLore(this.getItemMeta().getLore().stream().toArray(String[]::new));
        return swordItemStack;
    }

    @Override
    public SwordItemStack setInlay(InlaidGemItemStack inlaidGemItemStack, int indexSlot) {
        this.inlaidGems[indexSlot] = inlaidGemItemStack;
        return this;
    }

    @Override
    public SwordItemStack setInlay(Iterator<InlaidGemItemStack> itemStacks) {
        int i = 0;
        while (itemStacks.hasNext()) {
            this.setInlay(itemStacks.next(), i);
            i++;
        }
        return this;
    }

    public SwordItemStack setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    public SwordItemStack setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public SwordItemStack setName(String name) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta != null) itemMeta.setDisplayName(name);
        this.setItemMeta(itemMeta);
        displayName = name;

        return this;
    }

    public SwordItemStack setEnchantments(Enchantment enchantments) {
        this.enchantment = enchantments;
        return this;
    }

    public SwordItemStack setCustomAttackRange(double customAttackRange) {
        this.customAttackRange = customAttackRange;
        return this;
    }

    public double getDamage() {
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

    public WeaponItemWords getItemWords() {
        return itemWords;
    }

    public double getCustomAttackRange() {
        return customAttackRange;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }
}