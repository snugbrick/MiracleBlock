package github.snugbrick.miracleblock.items.skill;

public interface _Ability {
    default void run() {
        runTasks();
        genParticle();
    }

    void runTasks();

    void genParticle();
}
