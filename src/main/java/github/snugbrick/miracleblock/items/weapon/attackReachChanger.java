package github.snugbrick.miracleblock.items.weapon;

import github.snugbrick.miracleblock.items.ItemAdditional.ItemWords;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;

public class attackReachChanger implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand() instanceof SwordItemStack) {
                SwordItemStack swordItemStack = (SwordItemStack) player.getInventory().getItemInMainHand();
                double customAttackRange = swordItemStack.getCustomAttackRange();
                int damage = swordItemStack.getDamage();

                RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(),
                        player.getLocation().getDirection(), customAttackRange);

                if (result != null && result.getHitEntity() != null) {
                    Entity target = result.getHitEntity();
                    double distance = player.getLocation().distance(target.getLocation());

                    if (distance <= customAttackRange) {
                        if (target instanceof LivingEntity) {
                            LivingEntity livingTarget = (LivingEntity) target;
                            livingTarget.damage(damage, player);
                        } else event.setCancelled(true);
                    }
                }
            }
        }
    }
}