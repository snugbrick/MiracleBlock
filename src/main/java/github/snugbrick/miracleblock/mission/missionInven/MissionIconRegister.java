package github.snugbrick.miracleblock.mission.missionInven;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.api.Standardization;
import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 22:24
 */
public class MissionIconRegister {
    public void registerIcon() {
        MissionItemStack.registerMissionItemStack(new MissionItemStack(Standardization.missionStandardization(new ItemStack(Material.PAPER), "UNDONE", "UNDONE")), "UNDONE");
        MissionItemStack.registerMissionStatus(MissionItemStack.getMissionItemStack("UNDONE"), MissionStatus.UNDONE);

        ItemStack item = new ItemStack(Material.PAPER);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        MissionItemStack.registerMissionItemStack(new MissionItemStack(Standardization.missionStandardization(item, "COMPLETED", "COMPLETED")), "COMPLETED");
        MissionItemStack.registerMissionStatus(MissionItemStack.getMissionItemStack("COMPLETED"), MissionStatus.COMPLETED);

        MissionItemStack.registerMissionItemStack(new MissionItemStack(Standardization.missionStandardization(new ItemStack(Material.BOOK), "COLLECTED", "COLLECTED")), "COLLECTED");
        MissionItemStack.registerMissionStatus(MissionItemStack.getMissionItemStack("COLLECTED"), MissionStatus.COLLECTED);

        MissionItemStack.registerMissionItemStack(new MissionItemStack(Standardization.missionStandardization(new ItemStack(Material.BARRIER), "NULL", "NULL")), "NULL");

        MiracleBlock.getInstance().getLogger().info("MissionIcon & MissionItemStackStatus已注册完毕");
    }

    //对使用的ItemStack进行标准化改造 :3 --真是愚蠢

}
