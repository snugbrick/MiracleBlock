package github.snugbrick.miracleblock.items;

/**
 * 等级
 */
public enum ItemLevel {
    UR(0),
    SSR(1),
    SR(2),
    A(3),
    B(4),
    C(5);

    private final int level;

    ItemLevel(int level) {
        this.level = level;
    }

    public static ItemLevel getByString(String name) {
        for (ItemLevel levels : ItemLevel.values()) {
            if (levels.toString().equals(name)) {
                return levels;
            }
        }
        return null;
    }
}
