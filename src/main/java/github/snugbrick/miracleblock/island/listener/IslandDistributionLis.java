package github.snugbrick.miracleblock.island.listener;

import github.snugbrick.miracleblock.SQLMethods;
import github.snugbrick.miracleblock.island.IslandRegister;
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
            //查询已分配岛屿
            List<String> distributedIsland =
                    SQLMethods.QUERY.runTasks("island_distribution", "island_serial");
            //将玩家的岛屿写入数据库
            int serial = 0;
            if (distributedIsland != null) {
                serial = Integer.parseInt(distributedIsland.get(0));
                while (true) {
                    if (distributedIsland.iterator().hasNext()) {
                        int num = Integer.parseInt(distributedIsland.iterator().next());
                        if (num > serial) serial = num;
                    } else {
                        break;
                    }
                }
            } else {
                SQLMethods.INSERT.runTasks("island_distribution",
                        "player", player.getName(),
                        "uuid", playerUUID.toString(),
                        "island_serial", String.valueOf(serial));
            }
            SQLMethods.INSERT.runTasks("island_distribution",
                    "player", player.getName(),
                    "uuid", playerUUID.toString(),
                    "island_serial", String.valueOf(serial));
        }
        //传送玩家去岛屿
        List<String> playerIsland = SQLMethods.QUERY.runTasks("island_distribution",
                "island_serial", "player", player.getName());
        player.teleport(IslandRegister.getIsland(Integer.parseInt(playerIsland.get(0))).getSpawnPoint());


    }

    @EventHandler
    public void playerDeadSendBack(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getDamage() >= ((Player) e.getEntity()).getHealth()) {
            e.setCancelled(true);
            e.getEntity().teleport(Bukkit.getWorld("player_world").getSpawnLocation());
            ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
        }
    }
}
