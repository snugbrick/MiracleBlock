package github.snugbrick.miracleblock.items.skill;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/*
    震撼
 */
public class Shaking implements _Skill {
    private int cold_down;
    private final Player player;
    private final double range, damage;
    private final int vertigoTime;

    public Shaking(Player player, Vector kb, double range, double damage, int vertigoTime) {
        this.player = player;
        this.range = range;
        this.damage = damage;
        this.vertigoTime = vertigoTime;
    }

    @Override
    public void runTasks() {

    }

    @Override
    public void genParticle() {

    }

    @Override
    public void setColdDown(int cold_down) {
        this.cold_down = cold_down;
    }

    @Override
    public int getColdDown() {
        return cold_down;
    }
}
