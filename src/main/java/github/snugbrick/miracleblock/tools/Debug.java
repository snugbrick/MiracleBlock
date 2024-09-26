package github.snugbrick.miracleblock.tools;

import github.snugbrick.miracleblock.MiracleBlock;

public class Debug {
    /**
     * debug
     *
     * @param level 0info 1warning
     * @param msg   内容
     */
    public Debug(int level, String msg) {
        if (level == 0) {
            MiracleBlock.getInstance().getLogger().info(msg);
        } else if (level == 1) {
            MiracleBlock.getInstance().getLogger().warning(msg);
        }
    }
}
