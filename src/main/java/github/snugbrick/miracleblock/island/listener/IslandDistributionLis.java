package github.snugbrick.miracleblock.island.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.SQLMethods;
import github.snugbrick.miracleblock.island.IslandRegister;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.UUID;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.25 17:07
 */
public class IslandDistributionLis implements Listener {
    private UUID playerUUID;
    private Player player;
    private World world;


    @EventHandler
    public void newPlayerJoinLis(PlayerJoinEvent event) throws SQLException {
        player = event.getPlayer();
        playerUUID = player.getUniqueId();
        world = Bukkit.getWorld("player_world");
        if (world == null) return;

        //if (!player.hasPlayedBefore()) {
        int serial = 0;
        IslandRegister.IslandInformation iim = IslandRegister.getIsland(serial);

        //将玩家的岛屿写入数据库

        String back = SQLMethods.INSERT.runTasks("player_name",
                "player", player.getName(),
                "uuid", playerUUID.toString(),
                "island_serial", String.valueOf(iim.getSerial()));
        MiracleBlock.getInstance().getLogger().info(back);

        String aCheck = SQLMethods.QUERY.runTasks("player_name",
                "island_serial", "uuid", playerUUID.toString());
/*
        QueryAction queryAction = MiracleBlock.getSqlManager().createQuery()
                .inTable("player_name")
                .selectColumns("island_serial")
                .orderBy("id", false)
                .setPageLimit(0, 5)
                .build();

        AtomicReference<String> back2 = new AtomicReference<>();
        queryAction.executeAsync(successQuery -> {
            ResultSet resultSet1 = successQuery.getResultSet();

            back2.set(resultSet1.getString("island_serial"));
        });

        MiracleBlock.getInstance().getLogger().info(back2.toString());
*/

        //}

        player.teleport(world.getSpawnLocation());


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
