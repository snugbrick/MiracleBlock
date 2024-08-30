package github.snugbrick.miracleblock.island.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.island.IslandRegister;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.25 17:07
 */
public class IslandDistributionLis implements Listener {
    private UUID PlayerUUID;
    private Player player;
    private World world;


    @EventHandler
    public void newPlayerJoinLis(PlayerJoinEvent event) {
        player = event.getPlayer();
        PlayerUUID = player.getUniqueId();
        world = Bukkit.getWorld("player_world");
        if (world == null) return;

        PersistentDataContainer islandOwner = player.getPersistentDataContainer();
        //if (!player.hasPlayedBefore()) {
            IslandRegister.IslandInformation iim = new IslandRegister().getIsland(0);
            //将玩家的岛屿写入数据库
            MiracleBlock.getSqlManager().createTable(player.getName() + " " + player.getUniqueId());

            islandOwner.set(new NamespacedKey(MiracleBlock.getInstance(), "player_island"),
                    PersistentDataType.STRING, iim.getSpawnPoint().toString());
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
