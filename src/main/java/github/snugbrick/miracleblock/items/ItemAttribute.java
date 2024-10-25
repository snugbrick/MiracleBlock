package github.snugbrick.miracleblock.items;

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

    public static ItemAttribute getByByte(byte b) {
        return getByNumber(b & 0xFF);
    }

    public static byte getAttributeByByte(ItemAttribute attribute) {
        for (ItemAttribute attributes : ItemAttribute.values()) {
            if (attributes.equals(attribute)) {
                return (byte) attributes.attribute;
            }
        }
        return 0;
    }

    public int getAttribute() {
        return attribute;
    }

    public GainPackage getGain(int attribute) {
        switch (attribute) {
            case 0:
                return new GainPackage(1.1, 3, 1);
            case 1:
                return new GainPackage(1.0, 3, 1.1);
            case 2:
                return new GainPackage(1.2, 3, 0.9);
            case 3:
            case 4:
            case 5:
                return new GainPackage(1.0, 3, 1);
        }
        return null;
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

    public class GainPackage {
        private final double gainDamage;
        private final double gainReach;
        private final double gainAttackSpeed;

        public GainPackage(double gainDamage, double gainReach, double gainAttackSpeed) {
            this.gainDamage = gainDamage;
            this.gainAttackSpeed = gainAttackSpeed;
            this.gainReach = gainReach;
        }

        public double getGainAttackSpeed() {
            return gainAttackSpeed;
        }

        public double getGainReach() {
            return gainReach;
        }

        public double getGainDamage() {
            return gainDamage;
        }
    }
}
