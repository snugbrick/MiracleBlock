package github.snugbrick.miracleblock.tools;

import java.util.Random;

public class AboutRandom {
    public static boolean getRandom(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("百分比必须在0到100之间。");
        }

        int i = new Random().nextInt(100);
        return i < percent;
    }
}
