package github.snugbrick.miracleblock.items.weapon;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.CanInlaid;
import github.snugbrick.miracleblock.items.InlayItemStack.InlaidGemItemStack;
import github.snugbrick.miracleblock.items.ItemAttribute;
import github.snugbrick.miracleblock.items.ItemLevel;
import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import github.snugbrick.miracleblock.tools.NBT;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SwordItemStack extends MiraBlockItemStack implements CanInlaid {
    private double damage = 0;//
    private InlaidGemItemStack[] inlaidGems;
    private int slot;
    private ItemLevel level;//
    private ItemAttribute itemAttribute;//
    private WeaponItemWords itemWords;//
    private double customAttackRange;//
    private double attackSpeed = 1;//

    public SwordItemStack(ItemStack item, String key, String value, int inlaidSlot, ItemAttribute itemAttribute, ItemLevel level, WeaponItemWords itemwords) {
        super(NBT.setNBT(item, key, value), key, value);
        this.setLevel(level);
        this.setItemAttribute(itemAttribute);
        this.setItemWords(itemwords);
        this.setCustomAttackRange(3.0);
        this.setAttackSpeed(1.0);
        slot = inlaidSlot;
        inlaidGems = new InlaidGemItemStack[slot + 1];
    }

    public SwordItemStack(MiraBlockItemStack miracleBlockItemStack) {
        super(miracleBlockItemStack);
        ItemMeta meta = miracleBlockItemStack.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(MiracleBlock.getInstance(), "miracle_sword");

            if (container.has(key, PersistentDataType.STRING)) {
                this.damage = (Double.parseDouble(Objects.requireNonNull(getKeyValue("damage"), "Damage key not found")));
                this.attackSpeed = (Double.parseDouble(Objects.requireNonNull(getKeyValue("attackSpeed"), "Attack speed key not found")));
                this.customAttackRange = (Double.parseDouble(Objects.requireNonNull(getKeyValue("range"), "Range key not found")));

                this.level = ItemLevel.getByString(getKeyValue("level"));
                this.itemAttribute = ItemAttribute.getByString(getKeyValue("attribute"));
                this.itemWords = WeaponItemWords.getByString(getKeyValue("words"));

                for (int i = 0; i < slot; i++) {
                    String inlaidKey = getKeyValue("inlaid" + i);
                    if (inlaidKey != null) {
                        MiraBlockItemStack item = MiraBlockItemStack.getItem(inlaidKey);
                        inlaidGems[i] = new InlaidGemItemStack(item);
                    } else inlaidGems[i] = null;
                }
            }
        }
    }


    /**
     * 请在每个注册物品最后使用该方法 使物品得以完善
     *
     * @return 返回MiracleBlockItemStack 是这个类的终结链式方法
     */
    public MiraBlockItemStack buildSword() {
        //添加增益
        this.addGain();
        ItemMeta meta = this.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta != null) {
            if (itemAttribute != null) {
                lore.add("<=======物品属性=======>");
                lore.add(itemAttribute.toString());
            }
            if (itemWords != null) {
                lore.add("<=======物品词条=======>");
                lore.add(itemWords.toString());
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
                                i < inlaidGems.length ? "[" + (inlaidGems[i] == null ? "" : inlaidGems[i].toString()) + "]" : "[ ]")
                        .collect(Collectors.joining("  "));
                lore.add(result);
            }
            meta.setLore(lore);
        }
        this.setItemMeta(meta);

        return this;
    }

    public SwordItemStack setItemWords(WeaponItemWords itemWords) {
        this.itemWords = itemWords;
        WeaponItemWords.GainPackage gain = itemWords.getGain(itemWords.getLevel());
        ItemMeta meta = this.getItemMeta();

        if (meta != null && gain != null) {
            meta.setDisplayName(this.itemWords.toString() + " " +
                    Arrays.stream(meta.getDisplayName().split(" ", 2))
                            .skip(1)
                            .findFirst()
                            .orElse(meta.getDisplayName()));
            this.setDamage(this.damage *= gain.getGainDamage())
                    .setAttackSpeed(this.attackSpeed *= gain.getGainAttackSpeed())
                    .setCustomAttackRange(this.customAttackRange += gain.getGainReach());

            this.setItemMeta(meta);

            super.removeNSK("words");
            this.setKeyValue("words", itemWords.toString());
        }
        return this;
    }


    /**
     * 添加属性 将词条 属性加成赋予物品
     */
    public void addGain() {
        ItemMeta itemMeta = this.getItemMeta();
        ItemAttribute.GainPackage gainPackage = this.getItemAttribute().getGain(this.getItemAttribute().getAttribute());
        if (gainPackage != null) {
            this.setDamage(this.damage * gainPackage.getGainDamage());
            this.setAttackSpeed(this.attackSpeed * gainPackage.getGainAttackSpeed());
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
    }


    @Nonnull
    @Override
    public SwordItemStack clone() {
        SwordItemStack swordItemStack = (SwordItemStack) super.clone();
        swordItemStack.setItemWords(this.getItemWords())
                .setDamage(this.getDamage())
                .setAttackSpeed(this.getAttackSpeed())
                .setInlay(Arrays.stream(this.getInlaidGemItemStack()).iterator())
                //.setName(Objects.requireNonNull(this.getItemMeta()).getDisplayName())
                .setCustomAttackRange(this.getCustomAttackRange())
                .setLore(false, this.getItemMeta().getLore().stream().toArray(String[]::new));
        return swordItemStack;
    }

    @Override
    public SwordItemStack setInlay(InlaidGemItemStack inlaidGemItemStack, int indexSlot) {
        this.inlaidGems[indexSlot] = inlaidGemItemStack;
        if (!super.removeNSK("inlaid" + indexSlot)) {
            super.setKeyValue("inlaid" + indexSlot, inlaidGemItemStack.toString());
        }
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

    public SwordItemStack removeInlay(int indexSlot) {
        this.inlaidGems[indexSlot] = null;
        super.removeNSK("inlaid" + indexSlot);
        return this;
    }

    public SwordItemStack setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        if (!super.removeNSK("attackSpeed"))
            this.setKeyValue("attackSpeed", String.valueOf(this.getAttackSpeed()));
        return this;
    }

    public SwordItemStack setLevel(ItemLevel itemLevel) {
        this.level = itemLevel;
        if (!super.removeNSK("level"))
            this.setKeyValue("level", this.getLevel().toString());
        return this;
    }

    public SwordItemStack setDamage(double damage) {
        this.damage = damage;
        if (!super.removeNSK("damage"))
            this.setKeyValue("damage", String.valueOf(this.getDamage()));
        return this;
    }

    public SwordItemStack setCustomAttackRange(double customAttackRange) {
        this.customAttackRange = customAttackRange;
        if (!super.removeNSK("range"))
            this.setKeyValue("range", String.valueOf(this.getCustomAttackRange()));
        return this;
    }

    public SwordItemStack setItemAttribute(ItemAttribute itemAttribute) {
        this.itemAttribute = itemAttribute;
        if (!super.removeNSK("attribute"))
            this.setKeyValue("attribute", this.getItemAttribute().toString());
        return this;
    }


    public double getDamage() {
        return damage;
    }

    public int getSlotCount() {
        return slot;
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