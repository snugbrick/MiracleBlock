package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 22:24
 */
public class MissionIconRegister {
    public void registerIcon() {
        MissionItemStack.registerMissionItemStack(new MissionItemStack(new ItemStack(Material.PAPER), "UNDONE", "UNDONE"), "UNDONE");
        MissionItemStack.registerMissionStatus(MissionItemStack.getMissionItemStack("UNDONE"), MissionStatus.UNDONE);

        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        MissionItemStack.registerMissionItemStack(new MissionItemStack(item, "COMPLETED", "COMPLETED"), "COMPLETED");
        MissionItemStack.registerMissionStatus(MissionItemStack.getMissionItemStack("COMPLETED"), MissionStatus.COMPLETED);

        MissionItemStack.registerMissionItemStack(new MissionItemStack(new ItemStack(Material.BOOK), "COLLECTED", "COLLECTED"), "COLLECTED");
        MissionItemStack.registerMissionStatus(MissionItemStack.getMissionItemStack("COLLECTED"), MissionStatus.COLLECTED);

        MissionItemStack.registerMissionItemStack(new MissionItemStack(new ItemStack(Material.BARRIER), "NULL", "NULL"), "NULL");

        MiracleBlock.getInstance().getLogger().info("MissionIcon & MissionItemStackStatus已注册完毕");
    }
}
