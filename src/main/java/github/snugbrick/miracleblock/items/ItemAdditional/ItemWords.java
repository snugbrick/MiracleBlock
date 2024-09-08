package github.snugbrick.miracleblock.items.ItemAdditional;

import java.util.Random;

public enum ItemWords {
    LEGEND(0),
    SHINING(1),
    DEMON(2), // 恶魔
    UNREAL(3),
    RAGE(4),  // 暴怒
    DANGEROUS(5),
    WILD(6),  // 粗犷
    DEXTEROUS(7), // 灵巧
    COMMON(8),
    SADNESS(9),
    WEAK(10),
    HEAVY(11),
    POWERLESS(12),
    SHATTERED(13); // 碎裂

    private final int level;

    ItemWords(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static ItemWords getByLevel(int level) {
        for (ItemWords item : ItemWords.values()) {
            if (item.level == level) {
                return item;
            }
        }
        return null;
    }

    public static ItemWords getRandomItemWords() {
        int randomLevel = new Random().nextInt(14);
        return getByLevel(randomLevel);
    }

    @Override
    public String toString() {
        switch (this.level) {
            case 0:
                return "传说";
            case 1:
                return "光辉";
            case 2:
                return "恶魔";
            case 3:
                return "虚幻";
            case 4:
                return "暴怒";
            case 5:
                return "危险";
            case 6:
                return "粗犷";
            case 7:
                return "灵巧";
            case 8:
                return "平庸";
            case 9:
                return "忧伤";
            case 10:
                return "脆弱";
            case 11:
                return "沉重";
            case 12:
                return "无力";
            case 13:
                return "碎裂";
        }
        return null;
    }
}