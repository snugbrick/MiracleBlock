package github.snugbrick.miracleblock.items.skill;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 技能毗邻
 */
public class Adjacent implements Skills {
    private final Player triggerPlayer;
    private final Location targetLocation;
    private final double damage;
    private final int loopCount;

    public Adjacent(Player triggerPlayer, Location targetLocation, double damage, int loopCount) {
        this.triggerPlayer = triggerPlayer;
        this.targetLocation = targetLocation;
        this.damage = damage;
        this.loopCount = loopCount;
    }

    public Adjacent(Player triggerPlayer, Entity target, double damage, int loopCount) {
        this(triggerPlayer, target.getLocation(), damage, loopCount);
    }

    @Override
    public void run() {
        tasks();
    }

    @Override
    public void tasks() {
        if (targetLocation.getWorld() != null) {
            Collection<Entity> nearbyEntity = targetLocation.getWorld().getNearbyEntities(targetLocation, 1.0, 1.0, 1.0);
            Map<Double, Entity> allDistance = new HashMap<>();
            for (Entity entity : nearbyEntity) {

                allDistance.put(entity.getLocation().distance(targetLocation), entity);
            }

            for (int i = 0; i < loopCount; i++) {
                Entity closestEntity = allDistance.entrySet().stream()
                        .min(Map.Entry.comparingByKey())
                        .map(Map.Entry::getValue)
                        .orElse(null);
                if (closestEntity == null) break;

                allDistance.remove(allDistance.entrySet().stream()
                        .min(Map.Entry.comparingByKey())
                        .map(Map.Entry::getKey)
                        .orElse(0.0));

                if (closestEntity instanceof LivingEntity) {
                    LivingEntity livingTarget = (LivingEntity) closestEntity;
                    livingTarget.damage(damage, triggerPlayer);
                }
                particle();
            }
        }
    }

    @Override
    public void particle() {
        Location playerLocation = triggerPlayer.getLocation();
        Vector player2Target = playerLocation.toVector().subtract(targetLocation.toVector());
        Vector newPlayer2Target = player2Target.normalize().multiply(player2Target.length() - 1);
        triggerPlayer.getWorld().spawnParticle(Particle.SWEEP_ATTACK, newPlayer2Target.toLocation(triggerPlayer.getWorld()), 1);
    }
}
