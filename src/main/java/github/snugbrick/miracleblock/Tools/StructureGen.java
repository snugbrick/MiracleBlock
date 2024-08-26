package github.snugbrick.miracleblock.Tools;

import github.snugbrick.miracleblock.MiracleBlock;
import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.26 00:35
 */
@Deprecated
public class StructureGen {

    public void loadStructure(Location location) throws IOException {
        try {
            Bukkit.getLogger().info("正在准备生成结构");

            String path = MiracleBlock.instance.getConfig().getString("path");
            FileInputStream fis;
            if (path != null) {
                Bukkit.getLogger().info("成功获取到path,path是: " + path);

                fis = new FileInputStream(path);
                Bukkit.getLogger().info("输入流成功开启");

                NBTTagCompound nbt = NBTCompressedStreamTools.a(fis);
                NBTTagList blockList = nbt.getList("blocks", 10);
                if (blockList != null) Bukkit.getLogger().info("方块已获取");

                for (int i = 0; i < blockList.size(); i++) {
                    NBTTagCompound blockData = blockList.getCompound(i);
                    NBTTagList pos = blockData.getList("pos", 3);
                    if (pos != null) Bukkit.getLogger().info("位置已获取");

                    int x = pos.e(0);
                    int y = pos.e(1);
                    int z = pos.e(2);

                    String stateName = blockData.getString("state");
                    Bukkit.getLogger().info("尝试获取方块材料, stateName: " + stateName);
                    Material material = Material.getMaterial(stateName.toUpperCase());
                    if (material == null) {
                        Bukkit.getLogger().warning("未找到匹配的方块材料: " + stateName);
                        continue;
                    }

                    Location blockLocation = location.clone().add(x, y, z);
                    Bukkit.getLogger().info("正在生成");

                    blockLocation.getBlock().setType(material);
                }
                fis.close();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}