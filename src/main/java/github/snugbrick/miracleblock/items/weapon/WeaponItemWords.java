package github.snugbrick.miracleblock.items.weapon;

import java.util.Random;

public enum WeaponItemWords {
    LEGEND(0),
    SHINING(1),
    DEMON(2),
    UNREAL(3),
    RAGE(4),
    DANGEROUS(5),
    WILD(6),
    DEXTEROUS(7),
    COMMON(8),
    SADNESS(9),
    WEAK(10),
    HEAVY(11),
    POWERLESS(12),
    SHATTERED(13);

    private final int words;

    WeaponItemWords(int words) {
        this.words = words;
    }

    public int getLevel() {
        return words;
    }

    public static WeaponItemWords getByInt(int words) {
        for (WeaponItemWords item : WeaponItemWords.values()) {
            if (item.words == words) {
                return item;
            }
        }
        return null;
    }

    public static WeaponItemWords getByString(String name) {
        for (WeaponItemWords theWords : WeaponItemWords.values()) {
            if (theWords.toString() != null && theWords.toString().equals(name)) {
                return theWords;
            }
        }
        return null;
    }

    public static String getStringByWords(WeaponItemWords words) {
        for (WeaponItemWords theWords : WeaponItemWords.values()) {
            if (theWords.equals(words)) {
                return theWords.toString();
            }
        }
        return null;
    }

    public static WeaponItemWords getRandomItemWords(SwordItemStack swordItemStack) {
        int randomLevel = new Random().nextInt(14);
        return getByInt(randomLevel);
    }

    public GainPackage getGain(int level) {
        switch (level) {
            case 0:
                return new GainPackage(1.5, 4, 0.4);
            case 1:
                return new GainPackage(1.4, 4, 1);
            case 2:
                return new GainPackage(1.3, 3, 0.5);
            case 3:
                return new GainPackage(1.2, 3, 1.2);
            case 4:
            case 5:
                return new GainPackage(1.1, 3, 1);
            case 6:
                return new GainPackage(1.5, 3, 0.7);
            case 7:
                return new GainPackage(0.9, 3, 1.0);
            case 8:
                return new GainPackage(1.0, 3, 1.0);
            case 9:
                return new GainPackage(1.0, 3, 1.2);
            case 10:
                return new GainPackage(1.0, 3, 1.4);
            case 11:
                return new GainPackage(1.0, 3, 1.5);
            case 12:
                return new GainPackage(1.0, 2, 1.2);
            case 13:
                return new GainPackage(1.0, 2, 1.3);
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this.words) {
            case 0:
                return "传说的";
            case 1:
                return "光辉的";
            case 2:
                return "恶魔的";
            case 3:
                return "虚幻的";
            case 4:
                return "暴怒的";
            case 5:
                return "危险的";
            case 6:
                return "粗犷的";
            case 7:
                return "灵巧的";
            case 8:
                return "平庸的";
            case 9:
                return "忧伤的";
            case 10:
                return "脆弱的";
            case 11:
                return "沉重的";
            case 12:
                return "无力的";
            case 13:
                return "碎裂的";
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