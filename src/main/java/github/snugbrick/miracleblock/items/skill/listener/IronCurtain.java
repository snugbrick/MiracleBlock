package github.snugbrick.miracleblock.items.skill.listener;

import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.Objects;

public class IronCurtain implements Listener {
    @EventHandler
    public void ironCurtainHit(EntityDamageByEntityEvent event) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam("IronCurtain");
        if (team != null && !(event.getEntity() instanceof SkinnableEntity)) {
            event.setCancelled(true);
            Location aimLocation = event.getEntity().getLocation();
            Vector damager2Aim = event.getDamager().getLocation().toVector().subtract(aimLocation.toVector());

            if (aimLocation.getWorld() != null) {
                Location location = damager2Aim.normalize().multiply(damager2Aim.length() - 1).toLocation(aimLocation.getWorld());
                aimLocation.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,
                        location,
                        0, 0, 0, 0, 0, new Particle.DustOptions(Color.BLACK, 3));
            }
        }
    }
}
