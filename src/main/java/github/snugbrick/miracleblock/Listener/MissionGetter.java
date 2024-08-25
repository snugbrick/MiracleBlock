package github.snugbrick.miracleblock.Listener;

import github.snugbrick.miracleblock.Tools.AboutNBT;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.26 04:59
 */
public class MissionGetter implements Listener {
    private UUID PlayerUUID;
    private Player player;
    private World world;

    @EventHandler
    public void playerMissionGetter(PlayerJoinEvent e) {
        player = e.getPlayer();
        PlayerUUID = player.getUniqueId();
        world = e.getPlayer().getWorld();

        if (!player.hasPlayedBefore()) {
            ItemStack mission = AboutNBT.setCustomNBT(new ItemStack(Material.BOOK), "MissionBook", "0");
            ItemMeta itemMeta = mission.getItemMeta();
            if (itemMeta != null) itemMeta.setDisplayName("任务书");
            mission.setItemMeta(itemMeta);
            //TODO 丑陋的光效实现 以后再改:P
            mission.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

            player.getInventory().addItem(mission);
        }
    }
    @EventHandler
    public void playerMissionHandler(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            player = e.getPlayer();

            if (item != null && AboutNBT.hasCustomNBT(item, "MissionBook")) {
                //TODO 打开任务界面
            }
        }
    }
}
