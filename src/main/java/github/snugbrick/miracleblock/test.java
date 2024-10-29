package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import github.snugbrick.miracleblock.items.weapon.SwordItemStack;
import github.snugbrick.miracleblock.tools.Debug;
import github.snugbrick.miracleblock.tools.NBT;
import github.snugbrick.miracleblock.tools.NSK;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

public class test implements Listener {
    private final double customAttackRange = 10.0; // 自定义攻击距离

    //63号！到！77号！到！18号！到！
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            // 进行视线检测
            RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getLocation().getDirection(), customAttackRange);

            if (result != null && result.getHitEntity() != null) {
                Entity target = result.getHitEntity();

                // 检查目标是否在自定义范围内
                double distance = player.getLocation().distance(target.getLocation());

                if (distance <= customAttackRange) {
                    // 玩家可以攻击目标
                    // 在这里可以继续处理攻击逻辑
                } else {
                    // 超出自定义攻击距离，取消攻击
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            new Debug(0, "你所持有的物品是：" + itemInMainHand.toString() + "它属于ItemStack");

            MiraBlockItemStack mainItemStack = new MiraBlockItemStack(itemInMainHand);
            new Debug(0, "2你所持有的物品是：" + mainItemStack.toString() + "它属于MainItemStack");

            if (NSK.hasNameSpacedKey(mainItemStack, new NamespacedKey(MiracleBlock.getInstance(), "miracle_sword"))) {
                SwordItemStack swordItemStack = new SwordItemStack(mainItemStack);
                new Debug(0, "3你所持有的物品是：" + swordItemStack.toString() + "它属于SwordItemStack");

                new Debug(0, "其level是：" + swordItemStack.getLevel());
                new Debug(0, "其attackDamage是：" + swordItemStack.getDamage());
                //new Debug(0, "其attackSpeed是：" + swordItemStack.getAttackSpeed());
                new Debug(0, "其itemWords是：" + swordItemStack.getItemWords().toString());
                new Debug(0, "其itemAttribute是：" + swordItemStack.getItemAttribute().toString());
            }
        }
    }

    /**
     * {
     *     "parent": "item/generated",
     *     "textures": {
     *       "layer0": "miracle:item/dull_sword"
     *     }
     *   }
     */

}
