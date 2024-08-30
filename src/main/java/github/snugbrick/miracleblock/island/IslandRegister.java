package github.snugbrick.miracleblock.island;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.30 04:24
 */
public class IslandRegister {
    private static final Map<Integer, IslandInformation> islandInfors = new HashMap<>();

    public void registerIslandInformation(int x, int z, int serial) {
        islandInfors.put(serial, new IslandInformation(x, z));
    }

    public IslandInformation getIsland(int serial) {
        return islandInfors.get(serial);
    }

    public static class IslandInformation {
        private Chunk playerHome;
        private int homeX;
        private int homeZ;
        private final Location spawnPoint;

        public IslandInformation(int x, int z) {
            World world = Bukkit.getWorld("player_world");
            if (world != null) playerHome = world.getChunkAt(x, z);
            spawnPoint = new Location(world, x >> 4, 15, z >> 4);
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

        public Location getSpawnPoint() {
            return spawnPoint;
        }

    }
}
