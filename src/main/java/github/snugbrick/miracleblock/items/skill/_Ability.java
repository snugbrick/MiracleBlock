package github.snugbrick.miracleblock.items.skill;

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
