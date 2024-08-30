package github.snugbrick.miracleblock.mission.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.mission.MissionStatusHandler;
import github.snugbrick.miracleblock.mission.missionInven.MissionInventory;
import github.snugbrick.miracleblock.mission.missionInven.MissionItemStack;
import github.snugbrick.miracleblock.tools.AboutNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.26 04:59
 */
public class MissionHandler implements Listener {
    private Player player;

    @EventHandler
    public void playerMissionGetter(PlayerJoinEvent e) {
        player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            ItemStack mission = AboutNBT.setCustomNBT(new ItemStack(Material.BOOK), "MissionBook", "0");
            ItemMeta itemMeta = mission.getItemMeta();
            if (itemMeta != null) itemMeta.setDisplayName("任务书");
            mission.setItemMeta(itemMeta);
            mission.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);

            player.getInventory().addItem(mission);

            //将任务改为已完成
            MissionInventory mi = new MissionInventory();
            if (MissionItemStack.getMissionItemStackStatus(mi.getInventoryMissionItemStack(0)).equals(MissionStatus.UNDONE)) {
                //TODO 玩家任务隔离
                MissionInventory.addInventoryItem(MissionStatusHandler.getMissionIcon(MissionStatus.COMPLETED), 0);
            }
        }
    }

    @EventHandler
    public void playerMissionHandler(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            player = e.getPlayer();

            if (item != null && AboutNBT.hasCustomNBT(item, "MissionBook")) {
                new MissionInventory().openMissionInventory(player);
            }
        }
    }

    @EventHandler
    public void playerClickMissionInventory(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();

        if (currentItem != null && AboutNBT.hasCustomNBT(currentItem, "MissionIcons")) {
            MissionStatus missionStatus = MissionStatusHandler.getItemStackStatus(currentItem);
            MissionItemStack clickedIcon = MissionStatusHandler.getMissionIcon(missionStatus);

            if (e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP) {
                e.setCancelled(true);
            }

            MissionStatusHandler msh = new MissionStatusHandler();
            if (msh.isMissionDone(clickedIcon)) {
                //TODO 领取奖励操作 玩家任务隔离
                player.getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
                //设置为已收集
                MissionInventory.addInventoryItem(MissionStatusHandler.getMissionIcon(MissionStatus.COLLECTED), 0);
            } else {
                player.sendMessage("您未完成该任务");
            }
            e.setCancelled(true);
        }
    }

    public void onPlayerInPortal(PlayerPortalEvent e) {

    }


    //用于防止F快捷键获取
    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Bukkit.getScheduler().runTaskLater(MiracleBlock.getInstance(), () -> {
            cleanInventory(player);
            player.updateInventory();
        }, 3L);
    }

    private static void cleanInventory(Player player) {
        for (final ItemStack currentItem : player.getInventory().getContents()) {
            if (currentItem == null) continue;
            if (!AboutNBT.hasCustomNBT(currentItem, "MissionIcons")) continue;
            player.getInventory().remove(currentItem);
        }
        player.updateInventory();
    }
}
