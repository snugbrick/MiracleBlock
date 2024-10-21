package github.snugbrick.miracleblock.items.skill.listener;

import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class IronCurtain implements Listener {
    @EventHandler
    public void ironCurtainHit(EntityDamageByEntityEvent event) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam("IronCurtain");
        if (team != null && !(event.getEntity() instanceof SkinnableEntity)) {
            event.setCancelled(true);
            Location aimLocation = event.getEntity().getLocation();
            //Vector damager2Aim = event.getDamager().getLocation().toVector().subtract(aimLocation.toVector());

            if (aimLocation.getWorld() != null) {
                //Location location = damager2Aim.normalize().multiply(damager2Aim.length() - 1).toLocation(aimLocation.getWorld());
                Firework firework = aimLocation.getWorld().spawn(aimLocation, Firework.class);

                // 设置烟花的属性
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffect(FireworkEffect.builder()
                        .withColor(Color.BLACK) // 烟花颜色
                        .withFade(Color.GRAY) // 烟花渐变颜色
                        .with(FireworkEffect.Type.BALL) // 烟花类型
                        .trail(false) // 是否有拖尾
                        .flicker(true)// 是否闪烁
                        .build());

                meta.setPower(0); // 烟花的上升高度
                firework.setFireworkMeta(meta);
            }
        }
    }
}
