package github.snugbrick.miracleblock.island;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.SQLMethods;
import github.snugbrick.miracleblock.tools.Debug;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 00:37
 */
public class WorldGen {
    private World world;

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

    public void createPlayersRoom() throws SQLException {
        WorldCreator worldCreator = new WorldCreator("player_world");
        worldCreator.environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generator(new waterWorldGenerator());
        world = worldCreator.createWorld();
        if (world != null && Bukkit.getWorld("template_world") != null) {
            world.setSpawnLocation(0, 15, 0);
            worldStructureGen(Bukkit.getWorld("template_world"), world, 16);
            new Debug(0, "模板已经复制完毕");
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

    public Block[][][] copyStructure(World sourceWorld, int startX, int startZ) {
        Block[][][] structure = new Block[16][20][16];

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = 0; y < 40; y++) {
                    Block blockAt = sourceWorld.getBlockAt(x, y, z);
                    if (blockAt.getType() != Material.AIR) structure[x][y][z] = blockAt;
                }
            }
        }
        return structure;
    }


    public void pasteStructure(World targetWorld, Block[][][] theStructure, int offsetX, int offsetZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 40; y++) {
                    Block block = theStructure[x][y][z];
                    if (block != null)
                        targetWorld.getBlockAt(offsetX + x, y, offsetZ + z).setType(block.getType());
                }
            }
        }
    }

    public void worldStructureGen(World sourceWorld, World targetWorld, int numberOfStructures) throws SQLException {
        Block[][][] blocks = copyStructure(sourceWorld, 0, 0);
        new Debug(0, "模板结构已被装载");

        int serial_number = 0;
        int offset = 800;
        //正方形的边长
        int length = ((int) Math.sqrt(numberOfStructures));
        //右上顶点
        int offsetX = length / 2, offsetZ = length / 2;

        for (int i = 0; i < length; i++, serial_number++) {
            if (!skipStructureGen(serial_number)) {
                offsetX--;
                continue;
            }

            pasteStructure(targetWorld, blocks, offsetX * offset, offsetZ * offset);

            new IslandRegister().registerIslandInformation(offsetX * offset, offsetZ * offset, serial_number);
            new Debug(0, offsetX * offset + " " + offsetZ * offset + " 已加载结构，序列号：" + serial_number);
            offsetX--;
            if (i == length - 1) {
                i = -1;
                offsetX = length / 2;
                offsetZ--;
                if (offsetZ == -length / 2) break;
            }
        }
    }

    //为空则不可以跳过 也就是说 返回值为true则不可以跳过
    public boolean skipStructureGen(int serial) throws SQLException {
        List<String> hasSame = SQLMethods.QUERY.runTasks("island_distribution", "island_serial").stream()
                .filter(i -> Integer.parseInt(i) == serial).collect(Collectors.toList());
        return hasSame.isEmpty();
    }

    /**
     * 结构复制方法
     *
     * @param sourceWorld        模板世界
     * @param targetWorld        目标世界
     * @param numberOfStructures 结构数量
     */
    @Deprecated
    public void copyAndPasteStructure(World sourceWorld, World targetWorld, int numberOfStructures) {
        int x = 0;
        int z = 0;
        List<BlockData> blocksToCopy = new ArrayList<>();
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
        //注册岛屿
        int serialNum = 0;
        new IslandRegister().registerIslandInformation(targetChunk.getX(), targetChunk.getZ(), serialNum);
        serialNum++;
        targetChunk.unload(true);

        World world = Bukkit.getWorld("player_world");
        if (world == null) return;

        int length = ((int) Math.sqrt(numberOfStructures)) / 2;

        int zCounter = 0;

        for (int l = length; l >= 0; l--) {//2
            int offset = l * 800;

            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = world.getBlockAt(blockData.x + offset, blockData.y, z);
                targetBlock.setType(blockData.type);
            }
            new IslandRegister().registerIslandInformation(offset, z, serialNum);
            serialNum++;
            MiracleBlock.getInstance().getLogger().info(offset + " " + z + " 区块已完成结构加载与岛屿注册");

            if (l == 1) {
                z += offset;
                l = length;
                //抵消循环结束的自减一
                l++;
                zCounter++;//1
                if (zCounter == length) {
                    MiracleBlock.getInstance().getLogger().info("第一象限加载完成");
                    break;
                }
            }
        }

        x = 0;
        zCounter = 0;
        for (int l = length; l >= 0; l--) {
            int offset = l * 800;
            //
            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = world.getBlockAt(x, blockData.y, blockData.z + offset);
                targetBlock.setType(blockData.type);
            }
            new IslandRegister().registerIslandInformation(x, offset, serialNum);
            serialNum++;
            MiracleBlock.getInstance().getLogger().info(x + " " + offset + "区块已完成结构加载与岛屿注册");

            if (l == 1) {
                x -= offset;
                l = length;
                l++;
                zCounter++;
                if (zCounter >= length) {
                    MiracleBlock.getInstance().getLogger().info("第二象限加载完成");
                    break;
                }
            }
            //
        }

        z = 0;
        length = -length;//-5
        zCounter = 0;
        for (int l = length; l <= 0; l++) {
            int offset = l * 800;
            //
            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = world.getBlockAt(blockData.x + offset, blockData.y, z);
                targetBlock.setType(blockData.type);
            }
            new IslandRegister().registerIslandInformation(offset, z, serialNum);
            serialNum++;
            MiracleBlock.getInstance().getLogger().info(offset + " " + z + "区块已完成结构加载与岛屿注册");

            if (l == -1) {
                z += offset;
                l = length;
                l--;
                zCounter--;//-1 -2 -3 -4 -5
                if (zCounter <= length) {
                    MiracleBlock.getInstance().getLogger().info("第三象限加载完成");
                    break;
                }
            }
            //
        }

        x = 0;
        zCounter = 0;
        for (int l = length; l <= 0; l++) {
            int offset = l * 800;

            for (BlockData blockData : blocksToCopy) {
                Block targetBlock = world.getBlockAt(x, blockData.y, blockData.z + offset);
                targetBlock.setType(blockData.type);
            }
            new IslandRegister().registerIslandInformation(x, offset, serialNum);
            serialNum++;
            MiracleBlock.getInstance().getLogger().info(x + " " + offset + "区块已完成结构加载与岛屿注册");

            if (l == -1) {
                x += offset;
                l = length;
                l--;
                zCounter--;
                if (zCounter <= length) {
                    MiracleBlock.getInstance().getLogger().info("第四象限加载完成");
                    break;
                }
            }
        }
    }

    @Deprecated
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

