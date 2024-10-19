package github.snugbrick.miracleblock.items.weapon.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.skill.*;
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
    public void onPlayerInteract1(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();
            new WayOut(player, 5).run();
            new Illusion(player, 5).run();
            new IronCurtain(player, 5).run();
            new Adjacent(player, new Location(player.getWorld(), player.getLocation().getX() + 5, player.getLocation().getY() + 5, player.getLocation().getZ() + 5), 10, 2).run();
            new LightStrike(player.getWorld(), player.getLocation(), new Location(player.getWorld(), player.getLocation().getX() + 50, player.getLocation().getY(), player.getLocation().getZ() + 10)).run();
        }
    }

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
                        double damage = Integer.parseInt(damageS);
                        //开始玩家坐标
                        Location startLocation = player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(0.5));
                        //获得玩家视线
                        RayTraceResult result = player.getWorld().rayTraceEntities(startLocation,
                                player.getLocation().getDirection(), customAttackRange);

                        if (result != null && result.getHitEntity() != null) {
                            Entity target = result.getHitEntity();
                            //获得玩家距离目标生物距离
                            double distance = player.getLocation().distance(target.getLocation());
                            //转化为向量
                            Vector directionToPlayer = player.getLocation().toVector().subtract(target.getLocation().toVector());
                            //统一为单位向量
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
