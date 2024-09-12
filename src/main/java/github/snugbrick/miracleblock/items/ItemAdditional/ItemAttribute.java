package github.snugbrick.miracleblock.items.ItemAdditional;

import github.snugbrick.miracleblock.items.weapon.SwordItemStack;

/**
 * 属性
 */
public enum ItemAttribute {
    DARK_FLAME(0),//幽焰
    VIRTUAL_WAVE(1),//幻波
    ARRIS_GLOW(2),//棱光
    FROST_SOUL(3),//霜魂
    ITEM_SAYING(4),//物叙
    WOOD(5);//木

    private final int attribute;

    ItemAttribute(int attribute) {
        this.attribute = attribute;
    }

    public static ItemAttribute getByNumber(int number) {
        for (ItemAttribute attributes : ItemAttribute.values()) {
            if (attributes.attribute == number) {
                return attributes;
            }
        }
        return null;
    }

    public void addGain(SwordItemStack swordItemStack, ItemAttribute itemAttribute) {
        if (itemAttribute.attribute < 3) swordItemStack.setDamage(swordItemStack.getDamage() + 10);
        else swordItemStack.setDamage(swordItemStack.getDamage() + 5);
    }

    public int getLevel() {
        return attribute;
    }

    @Override
    public String toString() {
        switch (this.attribute) {
            case 0:
                return "幽焰";
            case 1:
                return "幻波";
            case 2:
                return "棱光";
            case 3:
                return "霜魂";
            case 4:
                return "物叙";
            case 5:
                return "木";
        }
        return null;
    }
}
