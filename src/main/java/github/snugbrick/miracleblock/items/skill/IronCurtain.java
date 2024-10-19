package github.snugbrick.miracleblock.items.skill;

import github.snugbrick.miracleblock.MiracleBlock;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.Random;

/**
 * 铁幕技能
 */
public class IronCurtain implements _Ability {
    private final Player player;
    private final int second;
    private boolean particleLock;

    public IronCurtain(Player player, int second) {
        this.player = player;
        this.second = second;
    }

    @Override
    public void runTasks() {
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
                    //刻意的闪白
                    team.removeEntry(player.getName());
                    player.removePotionEffect(PotionEffectType.GLOWING);
                }
            }
        }.runTaskLater(MiracleBlock.getInstance(), second * 20L);
    }

    @Override
    public void genParticle() {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam("IronCurtain");
        if (team == null) {
            team = scoreboard.registerNewTeam("IronCurtain");
            team.setColor(ChatColor.BLACK);
            team.setPrefix("铁幕§f");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        }
        team.addEntry(player.getName());
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, second * 20, 0));

        Location loc = player.getLocation();
        World world = player.getWorld();
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

                for (int i = 0; i < num; i++) {
                    world.spawnParticle(Particle.SMOKE_NORMAL,
                            //initialLocation.clone().add(xOffset, -reach, zOffset),
                            player.getLocation().add(
                                    random.nextBoolean() ? ((double) random.nextInt(5)) / 10 : -((double) random.nextInt(5)) / 10
                                    , 0,
                                    random.nextBoolean() ? ((double) random.nextInt(5)) / 10 : -((double) random.nextInt(5)) / 10),
                            0, 0.3, 0.5, 0.3, 0);
                }

                sec += move;
            }
        }.runTaskTimer(MiracleBlock.getInstance(), 0L, 3L);
    }

    @Override
    public void playSound() {

    }
}
