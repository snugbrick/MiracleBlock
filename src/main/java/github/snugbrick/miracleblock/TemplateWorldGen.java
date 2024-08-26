package github.snugbrick.miracleblock;

import org.bukkit.*;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 00:37
 */
public class TemplateWorldGen {
    private World world;

    public void createTempRoom() {

        WorldCreator worldCreator = new WorldCreator("template_world");
        worldCreator.environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generator(new VoidWorldGenerator());
        world = worldCreator.createWorld();
        if (world != null) {
            setupPlayerWorld(world);
            world.setSpawnLocation(0, 62, 0);
        }
        Bukkit.getLogger().info(world.getName() + " 已创建");
    }

    private void setupPlayerWorld(World world) {
        for (int x = 5; x >= -5; x--) {
            for (int z = 5; z >= -5; z--) {
                world.getBlockAt(x, 60, z).setType(Material.GRASS_BLOCK);
                world.getBlockAt(x, 59, z).setType(Material.STONE);
                world.getBlockAt(--x, 58, --z).setType(Material.IRON_ORE);
                world.getBlockAt(x - 2, 58, z - 2).setType(Material.DIAMOND_ORE);
            }
        }
        Location location = new Location(world, 0, 65, 3);
        world.generateTree(location, TreeType.TREE);
        genBox();

        world.setSpawnLocation(0, 62, 0);
    }

    private static class VoidWorldGenerator extends ChunkGenerator {
        @Override
        @Nonnull
        public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biomes) {
            return createChunkData(world);
        }
    }

    private void genBox() {
        for (int x = 15; x >= -15; x--) {
            for (int z = 15; z >= -15; z--) {
                world.getBlockAt(x, 55, z).setType(Material.BLACK_STAINED_GLASS);
                world.getBlockAt(x, 78, z).setType(Material.BLACK_STAINED_GLASS);

                world.getBlockAt(x, 54, z).setType(Material.BLACK_WOOL);
                world.getBlockAt(x, 79, z).setType(Material.BLACK_WOOL);
            }
        }

        for (int x = 15; x >= -15; x--) {
            for (int y = 55; y <= 78; y++) {
                world.getBlockAt(x, y, 14).setType(Material.BLACK_STAINED_GLASS);
                world.getBlockAt(x, y, -14).setType(Material.BLACK_STAINED_GLASS);

                world.getBlockAt(x, y, 15).setType(Material.BLACK_WOOL);
                world.getBlockAt(x, y, -15).setType(Material.BLACK_WOOL);
            }
        }

        for (int z = 14; z >= -14; z--) {
            for (int y = 55; y <= 78; y++) {
                world.getBlockAt(14, y, z).setType(Material.BLACK_STAINED_GLASS);
                world.getBlockAt(-14, y, z).setType(Material.BLACK_STAINED_GLASS);

                world.getBlockAt(15, y, z).setType(Material.BLACK_WOOL);
                world.getBlockAt(-15, y, z).setType(Material.BLACK_WOOL);
            }
        }
    }
}
