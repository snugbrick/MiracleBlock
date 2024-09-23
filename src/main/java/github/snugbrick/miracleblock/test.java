package github.snugbrick.miracleblock;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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

    /**
     * {
     *     "parent": "item/generated",
     *     "textures": {
     *       "layer0": "miracle:item/dull_sword"
     *     }
     *   }
     */

}
