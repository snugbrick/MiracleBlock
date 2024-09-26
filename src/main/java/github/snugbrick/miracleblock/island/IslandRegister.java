package github.snugbrick.miracleblock.island;

import github.snugbrick.miracleblock.SQLMethods;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.30 04:24
 */
public class IslandRegister {
    private static final Map<Integer, IslandInformation> islandInfors = new HashMap<>();

    public void registerIslandInformation(int x, int z, int serial) {
        islandInfors.put(serial, new IslandInformation(x, z, serial));
    }

    public static IslandInformation getIsland(int serial) {
        return islandInfors.get(serial);
    }

    public static IslandInformation getIsland(Player player) throws SQLException {
        List<String> strings = SQLMethods.QUERY.runTasks("island_distribution","island_serial", "player", player.getName());
        return islandInfors.get(Integer.parseInt(strings.get(0)));
    }


    public static class IslandInformation {
        private Chunk playerHome;
        private int homeX;
        private int homeZ;
        private final Location spawnPoint;

        private int serial;

        public IslandInformation(int x, int z, int serial) {
            World world = Bukkit.getWorld("player_world");
            if (world != null) playerHome = world.getChunkAt(x, z);
            spawnPoint = new Location(world, x + 8, 15, z + 8);
            this.serial = serial;
        }

        public Chunk getPlayerHomeChunk() {
            return playerHome;
        }

        public int getHomeX() {
            return homeX;
        }

        public int getHomeZ() {
            return homeZ;
        }

        public int getSerial() {
            return serial;
        }

        public Location getSpawnPoint() {
            return spawnPoint;
        }

    }
}
