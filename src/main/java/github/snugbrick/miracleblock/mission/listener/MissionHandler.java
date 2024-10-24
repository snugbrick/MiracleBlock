package github.snugbrick.miracleblock.mission.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.MainItemStack;
import github.snugbrick.miracleblock.mission.MissionStatus;
import github.snugbrick.miracleblock.mission.MissionStatusHandler;
import github.snugbrick.miracleblock.mission.PlayersMissionStatus;
import github.snugbrick.miracleblock.mission.missionInven.MissionInventory;
import github.snugbrick.miracleblock.mission.missionInven.MissionItemStack;
import github.snugbrick.miracleblock.mission.msg.MissionMsg;
import github.snugbrick.miracleblock.tools.AboutNBT;
import github.snugbrick.miracleblock.tools.LoadLangFiles;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.26 04:59
 */
public class MissionHandler implements Listener {
    private Player player;

    /**
     * 给予玩家任务书操作
     */
    @EventHandler
    public void playerMissionGetter(PlayerJoinEvent e) {
        player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            //给予任务书
            ItemStack mission = AboutNBT.setCustomNBT(new ItemStack(Material.BOOK), "MissionBook", "0");
            ItemMeta itemMeta = mission.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setDisplayName("任务书");
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            mission.setItemMeta(itemMeta);
            mission.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

            player.getInventory().addItem(mission);

            //开启任务一
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 13 * 20, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 13 * 20, 2));
            MissionMsg.mission1Msg(player);
        }
    }

    /**
     * 检测玩家打开任务书操作
     */
    @EventHandler
    public void playerMissionHandler(PlayerInteractEvent e) throws SQLException {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            player = e.getPlayer();

            if (item != null && AboutNBT.hasCustomNBT(item, "MissionBook")) {
                MissionInventory missionInventory = new MissionInventory(player);

                if (!PlayersMissionStatus.isCOLLECTED(player, LoadLangFiles.getMessageList("MissionName").get(0))) {
                    PlayersMissionStatus.setPlayerMissionStatus(player,
                            LoadLangFiles.getMessageList("MissionName").get(0), MissionStatus.COMPLETED);
                }
                missionInventory.openMissionInventory(player);
            }
        }
    }

    /**
     * 检测玩家点击任务书图标
     */
    @EventHandler
    public void playerClickMissionInventory(InventoryClickEvent e) throws SQLException {
        if (e.getWhoClicked() instanceof Player) player = (Player) e.getWhoClicked();
        else return;

        ItemStack currentItem = e.getCurrentItem();

        if (currentItem != null && AboutNBT.hasCustomNBT(currentItem, "MissionIcons")) {
            //player.getInventory().addItem(MiracleBlockItemStack.getMiracleBlockItemStack("dull_sword"));

            MissionStatus missionStatus = MissionStatusHandler.getItemStackStatus(currentItem);
            MissionItemStack clickedIcon = MissionStatusHandler.getMissionIcon(missionStatus);

            if (e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP) {
                e.setCancelled(true);
            }

            MissionStatusHandler msh = new MissionStatusHandler();
            if (msh.isMissionDone(player, clickedIcon)) {
                MissionInventory missionInventory = new MissionInventory(player);

                //领取奖励："钝剑" & 设置为已收集
                player.getInventory().addItem(MainItemStack.getItem("dull_sword"));
                PlayersMissionStatus.setPlayerMissionStatus(player,
                        LoadLangFiles.getMessageList("MissionName").get(e.getSlot()), MissionStatus.COLLECTED);

                missionInventory.openMissionInventory(player);
                player.sendMessage("您已收集该任务");
            } else if(msh.isMissionCollected(player,clickedIcon)){
                player.sendMessage("您已收集该任务");
            }else {
                player.sendMessage("您未完成该任务");
            }
            e.setCancelled(true);
        }
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
