package github.snugbrick.miracleblock.island.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.SQLMethods;
import github.snugbrick.miracleblock.island.IslandRegister;
import github.snugbrick.miracleblock.tools.Debug;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.25 17:07
 */
public class IslandDistributionLis implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void newPlayerJoinLis(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        World world = Bukkit.getWorld("player_world");
        if (world == null) return;

        if (!player.hasPlayedBefore()) {
            Bukkit.getScheduler().runTaskAsynchronously(MiracleBlock.getInstance(), () -> {
                //查询已分配岛屿
                List<String> distributedIsland;
                try {
                    distributedIsland = SQLMethods.QUERY.runTasks("island_distribution", "island_serial");

                    //将玩家的岛屿写入数据库
                    int serial = 0;
                    if (distributedIsland != null) {
                        if(!distributedIsland.isEmpty()) {
                            serial = distributedIsland.stream()
                                    .mapToInt(Integer::parseInt)
                                    .max()
                                    .orElse(0);

                            SQLMethods.INSERT.runTasks("island_distribution",
                                    "player", player.getName(),
                                    "uuid", playerUUID.toString(),
                                    "island_serial", String.valueOf(serial + 1));
                            tpPlayerToIsland(player);
                        }
                    } else {
                        SQLMethods.INSERT.runTasks("island_distribution",
                                "player", player.getName(),
                                "uuid", playerUUID.toString(),
                                "island_serial", String.valueOf(serial));
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            tpPlayerToIsland(player);
        }
    }

    private void tpPlayerToIsland(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(MiracleBlock.getInstance(), () -> {
            try {
                List<String> playerIsland = SQLMethods.QUERY.runTasks("island_distribution",
                        "island_serial", "player", player.getName());
                int islandSerial = Integer.parseInt(playerIsland.get(0));
                Bukkit.getScheduler().runTask(MiracleBlock.getInstance(), () -> {
                    new Debug(0, "已将玩家传送至" + islandSerial + "号岛屿，玩家名：" + player.getName());
                    player.teleport(IslandRegister.getIsland(islandSerial).getSpawnPoint());
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventHandler
    public void playerDeadSendBack(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getDamage() >= ((Player) e.getEntity()).getHealth()) {
            e.setCancelled(true);
            tpPlayerToIsland(((Player) e.getEntity()).getPlayer());
            ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
        }
    }
}
