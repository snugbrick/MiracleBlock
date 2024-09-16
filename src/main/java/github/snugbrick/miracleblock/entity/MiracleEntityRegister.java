package github.snugbrick.miracleblock.entity;

import github.snugbrick.miracleblock.entity.monster.MiracleEntitySlime;

public class MiracleEntityRegister {
    public static void registerMiracleEntity() throws Exception {
        MiracleEntitySlime.registerSlime();
    }
}
