package github.snugbrick.miracleblock;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 00:37
 */
public class WorldGen {
    private World world;

    private static final List<BlockData> blocksToCopy = new ArrayList<>();

    public void createTempRoom() {

        WorldCreator worldCreator = new WorldCreator("template_world");
        worldCreator.environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generator(new waterWorldGenerator());
        world = worldCreator.createWorld();
        if (world != null) {
            world.setSpawnLocation(0, 20, 0);
        }
        Bukkit.getLogger().info(world.getName() + " 已创建");
    }

    public void createPlayersRoom() {
        WorldCreator worldCreator = new WorldCreator("player_world");
        worldCreator.environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generator(new waterWorldGenerator());
        world = worldCreator.createWorld();
        if (world != null && Bukkit.getWorld("template_world") != null) {
            world.setSpawnLocation(0, 15, 0);
            copyAndPasteStructure(Bukkit.getWorld("template_world"), world, 16);
            MiracleBlock.getInstance().getLogger().info("模板复制已完成");
        }
        Bukkit.getLogger().info(world.getName() + " 已创建");
    }

    public static class waterWorldGenerator extends ChunkGenerator {

        @Nonnull
        @Override
        public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
            ChunkData chunkData = createChunkData(world);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 7; y <= 8; y++) chunkData.setBlock(x, y, z, Material.WATER);
                    for (int y = 4; y <= 6; y++) chunkData.setBlock(x, y, z, Material.SAND);
                    for (int y = 2; y <= 3; y++) chunkData.setBlock(x, y, z, Material.GRAVEL);
                    chunkData.setBlock(x, 1, z, Material.BEDROCK);
                }
            }
            return chunkData;
        }
    }

    public void copyAndPasteStructure(World sourceWorld, World targetWorld, int numberOfStructures) {
        int x = 0;
        int z = 0;
        Chunk sourceChunk = sourceWorld.getChunkAt(x, z);
        sourceChunk.load();
        MiracleBlock.getInstance().getLogger().info("目标区块已加载");

        for (int cx = 0; cx < 16; cx++) {
            for (int cz = 0; cz < 16; cz++) {
                for (int y = 0; y < 20; y++) {
                    Block block = sourceChunk.getBlock(cx, y, cz);
                    if (block.getType() != Material.AIR) {
                        blocksToCopy.add(new BlockData(block.getType(), cx, y, cz));
                    }
                }
            }
        }
        MiracleBlock.getInstance().getLogger().info("目标区块所有结构已加载");

        Chunk targetChunk = targetWorld.getChunkAt(0, 0);
        targetChunk.load();
        for (BlockData blockData : blocksToCopy) {
            Block targetBlock = targetChunk.getBlock(blockData.x, blockData.y, blockData.z);
            targetBlock.setType(blockData.type);
        }
        targetChunk.unload(true);


        int length = ((int) Math.sqrt(numberOfStructures)) / 2;//5
        int zCounter = 0;
        for (int l = length; l >= 0; l--) {
            int offset = l * 800;
            //
            Chunk targetChunk0 = targetWorld.getChunkAt(x + offset, z);
            //
            MiracleBlock.getInstance().getLogger().info((x + offset) + " " + z + "区块已加载");
            targetChunk0.load();

            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = targetChunk0.getBlock(blockData.x, blockData.y, blockData.z);
                targetBlock.setBlockData(blockData.type.createBlockData());

                targetBlock.getState().update(true, false);
            }

            if (l == 1) {
                z += offset;
                l = length;
                zCounter++;
                if (zCounter >= length) {
                    targetChunk0.unload(true);
                    MiracleBlock.getInstance().getLogger().info("第一象限加载完成");
                    break;
                }
            }

            //
            targetChunk0.unload(true);
        }

        x = 0;
        z = 0;
        zCounter = 0;
        for (int l = length; l >= 0; l--) {
            int offset = l * 800;
            Chunk targetChunk0 = targetWorld.getChunkAt(x, z + offset);
            //
            MiracleBlock.getInstance().getLogger().info(x + " " + (z + offset) + "区块已加载");
            targetChunk0.load();

            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = targetChunk0.getBlock(blockData.x, blockData.y, blockData.z);
                targetBlock.setType(blockData.type);
            }

            if (l == 1) {
                x += offset;
                l = length;
                zCounter++;
                if (zCounter >= length) {
                    targetChunk0.unload(true);
                    MiracleBlock.getInstance().getLogger().info("第二象限加载完成");
                    break;
                }
            }
            //
            targetChunk0.unload(true);
        }

        x = 0;
        z = 0;
        length = -length;//-5
        zCounter = 0;
        for (int l = length; l <= 0; l++) {
            int offset = l * 800;
            Chunk targetChunk0 = targetWorld.getChunkAt(x - offset, z);
            //
            MiracleBlock.getInstance().getLogger().info((x - offset) + " " + z + "区块已加载");
            targetChunk0.load();

            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = targetChunk0.getBlock(blockData.x, blockData.y, blockData.z);
                targetBlock.setType(blockData.type);
            }

            if (l == -1) {
                z += offset;
                l = length;
                zCounter--;//-1 -2 -3 -4 -5
                if (zCounter <= length) {
                    targetChunk0.unload(true);
                    MiracleBlock.getInstance().getLogger().info("第三象限加载完成");
                    break;
                }
            }
            //
            targetChunk0.unload(true);
        }

        x = 0;
        z = 0;
        zCounter = 0;
        for (int l = length; l <= 0; l++) {
            int offset = l * 800;
            Chunk targetChunk0 = targetWorld.getChunkAt(x, z - offset);
            //
            MiracleBlock.getInstance().getLogger().info(x + " " + (z - offset) + "区块已加载");
            targetChunk0.load();

            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = targetChunk0.getBlock(blockData.x, blockData.y, blockData.z);
                targetBlock.setType(blockData.type);
            }

            if (l == -1) {
                x += offset;
                l = length;
                zCounter--;
                if (zCounter <= length) {
                    targetChunk0.unload(true);
                    MiracleBlock.getInstance().getLogger().info("第四象限加载完成");
                    break;
                }
            }
            targetChunk0.unload(true);
        }
    }

    private class BlockData {
        public Material type;
        public int x, y, z;

        public BlockData(Material type, int x, int y, int z) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

/*===================================================================
        for (int i = 0; i < numberOfStructures; i++) {
            int offsetX = i * 1500;


            if (i < 4) {
                Chunk targetChunk = targetWorld.getChunkAt(x, z + offsetX);
                targetChunk.load(true);
            } else if (4 < i && i < 8) {
                Chunk targetChunk = targetWorld.getChunkAt(x, z - offsetX);
                targetChunk.load(true);
            }

            for (int cx = 0; cx < 16; cx++) {
                for (int cz = 0; cz < 16; cz++) {
                    for (int y = 0; y <= 20; y++) {

                        Block sourceBlock = sourceChunk.getBlock(cx, y, cz);
                        Block targetBlock = targetChunk.getBlock(cx, y, cz);

                        if (sourceBlock.getType() != Material.AIR) {
                            targetBlock.setType(sourceBlock.getType());

                            BlockState sourceState = sourceBlock.getState();
                            if (sourceState instanceof TileState) {
                                targetBlock.getState().update(true, false);
                            }
                        }
                    }
                }
            }
        }


        int chunkX = startLocation.getChunk().getX();
        int chunkZ = startLocation.getChunk().getZ();

        List<BlockData> blocksToCopy = new ArrayList<>();

        for (int x = chunkX * 16; x < (chunkX + 1) * 16; x++) {
            for (int y = 0; y < 20; y++) {
                for (int z = chunkZ * 16; z < (chunkZ + 1) * 16; z++) {
                    Block block = sourceWorld.getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR) {
                        blocksToCopy.add(new BlockData(block.getType(), x, y, z));
                    }
                }
            }
        }

        for (BlockData blockData : blocksToCopy) {
            Location pasteLocation = new Location(targetWorld, blockData.x, blockData.y, blockData.z);
            Block targetBlock = targetWorld.getBlockAt(pasteLocation);
            targetBlock.setType(blockData.type);
        }

        for (int i = 1; i < numberOfStructures; i++) {
            int offsetX = i * 1500;

            for (BlockData blockData : blocksToCopy) {
                Location pasteLocation = new Location(targetWorld, blockData.x + offsetX, blockData.y, blockData.z);
                Block targetBlock = targetWorld.getBlockAt(pasteLocation);
                targetBlock.setType(blockData.type);

                Location pasteLocation2 = new Location(targetWorld, blockData.x, blockData.y + offsetX, blockData.z);
                Block targetBlock2 = targetWorld.getBlockAt(pasteLocation2);
                targetBlock2.setType(blockData.type);

                Location pasteLocation3 = new Location(targetWorld, blockData.x, blockData.y, blockData.z + offsetX);
                Block targetBlock3 = targetWorld.getBlockAt(pasteLocation3);
                targetBlock3.setType(blockData.type);
            }
        }
    }
        //下面的都是小黑屋世界的，已弃用=================================================================================
        private void setupPlayerWorld (World world){
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

        @Deprecated

        private static class VoidWorldGenerator extends ChunkGenerator {
            @Override
            @Nonnull
            public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biomes) {
                return createChunkData(world);
            }
        }

        private void genBox () {
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
}*/
