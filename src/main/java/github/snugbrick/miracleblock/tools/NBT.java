package github.snugbrick.miracleblock.tools;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.26 05:10
 */
public class NBT {

    public static ItemStack setNBT(ItemStack item, String key, String value) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        if (tagCompound != null) tagCompound.setString(key, value);

        nmsItem.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean hasCustomNBT(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        if (nmsItem.hasTag()) {
            NBTTagCompound tagCompound = nmsItem.getTag();
            if (tagCompound != null) return tagCompound.hasKey(key);
        }
        return false;
    }

    public static String getCustomNBT(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        if (nmsItem.hasTag()) {
            NBTTagCompound tagCompound = nmsItem.getTag();
            if (tagCompound != null) return tagCompound.getString(key);
        }
        return null;
    }

    public static Boolean removeNBT(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        if (tagCompound != null) {
            tagCompound.remove(key);
            nmsItem.setTag(tagCompound);
            return true;
        }
        return false;
    }
}
