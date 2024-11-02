package github.snugbrick.miracleblock.items.skill;

import com.mysql.jdbc.StreamingNotifiable;

public interface _Ability {
    default void run() {
        runTasks();
        genParticle();
        playSound();
    }

    void runTasks();
    void genParticle();
    void playSound();
}
