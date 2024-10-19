package github.snugbrick.miracleblock.items.skill;

import org.bukkit.entity.Player;

public interface _Skill extends _Ability {
    void setColdDown(int cold_down);

    int getColdDown();

    default void runColdDown(Player player, int itemSerial) {

    }
}
