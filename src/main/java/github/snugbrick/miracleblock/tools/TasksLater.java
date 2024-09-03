package github.snugbrick.miracleblock.tools;

import github.snugbrick.miracleblock.MiracleBlock;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TasksLater {
    public static void sendMission(Player player, String msg, int delay) {
        Bukkit.getScheduler().runTaskLater(MiracleBlock.getInstance(), () -> {
            player.sendMessage(msg);
            Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0, 0);
        }, delay);
    }

    public static void sendMsgLater(Player player, String msg, int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(msg);
                switch (new Random().nextInt(7 + 1)) {
                    case (1):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 0, 0);
                        break;
                    case (2):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 0, 0);
                        break;
                    case (3):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 0, 0);
                        break;
                    case (4):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0, 0);
                        break;
                    case (5):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0, 0);
                        break;
                    case (6):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 0, 0);
                        break;
                    case (7):
                        Bukkit.getWorld("player_world").playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 0, 0);
                        break;
                }
            }
        }.runTaskLater(MiracleBlock.getInstance(), 20L * delay);
    }
}
