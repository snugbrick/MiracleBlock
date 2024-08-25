package github.snugbrick.miracleblock.Tools;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.26 00:35
 */

import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StructureGen extends JavaPlugin {

    public void loadStructure(Player player, File nbtFile, Location location) throws IOException {
        try {
            String path = getConfig().getString("Path");
            FileInputStream fis;
            if (path != null) {
                fis = new FileInputStream(path);
                NBTTagCompound nbt = NBTCompressedStreamTools.a(fis);
                NBTTagList sizeList = nbt.getList("size", 3);

                int sizeX = sizeList.e(0);
                int sizeY = sizeList.e(1);
                int sizeZ = sizeList.e(2);

                NBTTagList blockList = nbt.getList("blocks", 10);

                for (int i = 0; i < blockList.size(); i++) {
                    NBTTagCompound blockData = blockList.getCompound(i);
                    NBTTagList pos = blockData.getList("pos", 3);

                    int x = pos.e(0);
                    int y = pos.e(1);
                    int z = pos.e(2);

                    String stateName = blockData.getString("state");

                    Material material = Material.getMaterial(stateName.toUpperCase());
                    if (material == null) continue;

                    Location blockLocation = location.clone().add(x, y, z);

                    blockLocation.getBlock().setType(material);
                }
                fis.close();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}