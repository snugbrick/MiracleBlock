package github.snugbrick.miracleblock.items.weapon.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Objects;

public class attackReachChanger implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            Player player = event.getPlayer();
            ItemStack itemStack = event.getItem();

            if (itemStack != null && itemStack.getItemMeta() != null) {
                PersistentDataContainer container = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer();

                if (container.has(new NamespacedKey(MiracleBlock.getInstance(), "miracle_sword"), PersistentDataType.STRING)) {
                    String range = container.get(new NamespacedKey(MiracleBlock.getInstance(), "range"), PersistentDataType.STRING);
                    String damageS = container.get(new NamespacedKey(MiracleBlock.getInstance(), "damage"), PersistentDataType.STRING);

                    if (range != null && damageS != null) {

                        double customAttackRange = Double.parseDouble(range);
                        customAttackRange -= 0.5;
                        int damage = Integer.parseInt(damageS);
                        Location startLocation = player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(0.5));

                        RayTraceResult result = player.getWorld().rayTraceEntities(startLocation,
                                player.getLocation().getDirection(), customAttackRange);

                        if (result != null && result.getHitEntity() != null) {
                            Entity target = result.getHitEntity();
                            double distance = player.getLocation().distance(target.getLocation());

                            Vector directionToPlayer = player.getLocation().toVector().subtract(target.getLocation().toVector());
                            directionToPlayer.normalize();
                            Location newLocation = target.getLocation().add(directionToPlayer.multiply(1));
                            //重设距离 保证举例检测是玩家视线到敌人碰撞箱边
                            distance = newLocation.distance(player.getLocation());

                            if (distance <= customAttackRange) {
                                if (target instanceof LivingEntity) {
                                    LivingEntity livingTarget = (LivingEntity) target;
                                    livingTarget.damage(damage, player);
                                } else {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
