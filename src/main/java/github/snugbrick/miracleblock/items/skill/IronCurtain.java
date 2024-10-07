package github.snugbrick.miracleblock.items.skill;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.tools.Debug;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;

/**
 * 铁幕技能
 */
public class IronCurtain implements Skills, Listener {
    private final Player player;
    private final int second;
    private boolean particleLock;

    public IronCurtain(Player player, int second) {
        this.player = player;
        this.second = second;
    }

    @Override
    public void tasks() {
        player.setInvulnerable(true);
        particleLock = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setInvulnerable(false);
                particleLock = false;

                Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                Team team = scoreboard.getTeam("IronCurtain");
                if (team != null) {
                    team.unregister();
                }
            }
        }.runTaskLater(MiracleBlock.getInstance(), second * 20L);
        new Debug(0, "普通任务正常运行");
    }

    @Override
    public void particle() {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam("IronCurtain");
        if (team == null) {
            team = scoreboard.registerNewTeam("IronCurtain");
        }

        team.setColor(ChatColor.BLACK);
        team.setPrefix("铁幕");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        team.addEntry(player.getName());
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, second * 20, 0));

        Location loc = player.getLocation();
        World world = player.getWorld();
        Random random = new Random();

        new BukkitRunnable() {
            final double move = 0.05;
            double sec = 0;
            final double maxSec = second * 20;
            final Location initialLocation = loc.clone();
            final Random random = new Random();
            final int num = 35;

            @Override
            public void run() {
                if (!particleLock) {
                    cancel();
                    return;
                }
                double xOffset = (random.nextDouble() * 0.4) - 0.2;
                double zOffset = (random.nextDouble() * 0.4) - 0.2;

                for (int i = 0; i < num; i++) {
                    world.spawnParticle(Particle.SMOKE_NORMAL,
                            //initialLocation.clone().add(xOffset, -reach, zOffset),
                            player.getLocation(),
                            1, 0.3, 0.5, 0.3, 0);
                }

                sec += move;
            }
        }.runTaskTimer(MiracleBlock.getInstance(), 0L, 3L);
        new Debug(0, "粒子任务正常运行");
    }

    @EventHandler
    public void ironCurtainHit(EntityDamageByEntityEvent event) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam("IronCurtain");
        if (team != null) {
            event.setCancelled(true);
            Location aimLocation = event.getEntity().getLocation();
            Vector damager2Aim = event.getDamager().getLocation().toVector().subtract(aimLocation.toVector());

            if (aimLocation.getWorld() != null) {
                Location location = damager2Aim.normalize().multiply(damager2Aim.length() - 1).toLocation(aimLocation.getWorld());
                aimLocation.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,
                        location,
                        1, 0, 0, 0, 0);
            }
        }
    }
}
