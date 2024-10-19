package github.snugbrick.miracleblock.items.skill;

import github.snugbrick.miracleblock.MiracleBlock;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

/*
    出路
 */
public class WayOut implements _Skill {
    private int cold_down;
    private final Player player;
    private final int smokeTime;
    private boolean particleLock;

    public WayOut(Player player, int smokeTime) {
        this.player = player;
        this.smokeTime = smokeTime;
    }

    @Override
    public void runTasks() {
        Vector direction = player.getLocation().getDirection();
        direction.multiply(-1)
                .setY(0)
                .multiply(1.5);
        player.setVelocity(direction);
        particleLock = true;
        new BukkitRunnable() {

            @Override
            public void run() {
                particleLock = false;
            }
        }.runTaskLater(MiracleBlock.getInstance(), smokeTime * 20L);
    }

    @Override
    public void genParticle() {
        new BukkitRunnable() {
            final double move = 0.05;
            double sec = 0;
            final Random random = new Random();
            final int num = 35;
            final Location triggerLoc =player.getLocation();

            @Override
            public void run() {
                if (!particleLock) {
                    cancel();
                    return;
                }

                for (int i = 0; i < num; i++) {
                    player.getWorld().spawnParticle(Particle.REDSTONE,
                            //initialLocation.clone().add(xOffset, -reach, zOffset),
                            triggerLoc.clone().add(
                                    random.nextBoolean() ? ((double) random.nextInt(30)) / 10 : -((double) random.nextInt(30)) / 10,
                                    random.nextBoolean() ? ((double) random.nextInt(30)) / 10 : -((double) random.nextInt(30)) / 10,
                                    random.nextBoolean() ? ((double) random.nextInt(30)) / 10 : -((double) random.nextInt(30)) / 10),
                            0, 0.3, 0.5, 0.3,
                            new Particle.DustOptions(Color.GRAY, 10F));
                }

                sec += move;
            }
        }.runTaskTimer(MiracleBlock.getInstance(), 0L, 3L);
    }

    @Override
    public void playSound() {

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