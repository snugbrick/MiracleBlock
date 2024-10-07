package github.snugbrick.miracleblock.items.skill;

import github.snugbrick.miracleblock.tools.Debug;

public interface Skills {
    default void run() {
        new Debug(0,"已经运行了run");
        tasks();
        particle();
    }

    void tasks();

    void particle();
}
