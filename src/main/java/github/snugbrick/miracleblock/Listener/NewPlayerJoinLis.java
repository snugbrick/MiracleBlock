package github.snugbrick.miracleblock.Listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;
import java.util.UUID;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.25 17:07
 */
public class NewPlayerJoinLis implements Listener {
    private UUID PlayerUUID;
    private Player player;
    private World world;

    @EventHandler
    public void _NewPlayerJoinLis(PlayerJoinEvent event) {
        player = event.getPlayer();
        PlayerUUID = player.getUniqueId();
        world = event.getPlayer().getWorld();

        if (!event.getPlayer().hasPlayedBefore() && world != Bukkit.getWorld("the_world_of_" + PlayerUUID.toString())) {
            createNewRoom();
        }
        //TODO
        createNewRoom();
    }

    private void createNewRoom() {
        WorldCreator worldCreator = new WorldCreator("the_world_of_" + PlayerUUID.toString());
        worldCreator.environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generator(new VoidWorldGenerator());
        world = worldCreator.createWorld();
        if (world != null) {
            setupPlayerWorld(world, player);
            player.teleport(world.getSpawnLocation());
        }
        Bukkit.getLogger().info("the_world_of_" + PlayerUUID.toString() + " 已创建");
    }

    private void setupPlayerWorld(World world, Player player) {
        world.getBlockAt(0, 60, 0).setType(Material.GRASS_BLOCK);
        genBox();

        world.setSpawnLocation(0, 60, 0);
    }

    private static class VoidWorldGenerator extends ChunkGenerator {

        @Override
        public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
            return createChunkData(world);
        }

        @Override
        public boolean shouldGenerateStructures() {
            return false;
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
