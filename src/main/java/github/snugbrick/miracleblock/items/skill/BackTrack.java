package github.snugbrick.miracleblock.items.skill;

import github.snugbrick.miracleblock.MiracleBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/*
    回溯
 */
public class BackTrack implements _Ability {
    private final Player targetPlayer;
    private final org.bukkit.Location aimLocation;
    private final int second;
    private final boolean lock;

    public BackTrack(Player targetPlayer, org.bukkit.Location aimLocation, int second) {
        this.targetPlayer = targetPlayer;
        this.aimLocation = aimLocation;
        this.second = second;
        this.lock = true;
    }

    @Override
    public void runTasks() {
        targetPlayer.sendMessage("你的回溯记录点是: " + aimLocation.toString());
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(MiracleBlock.getInstance(), second * 20L, 20L);
    }

    @Override
    public void genParticle() {

    }

    @Override
    public void playSound() {

    }
}
