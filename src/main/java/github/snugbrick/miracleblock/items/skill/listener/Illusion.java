package github.snugbrick.miracleblock.items.skill.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class Illusion implements Listener {
    @EventHandler
    public void playerHitFakePlayer(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof SkinnableEntity) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(e.getEntity());

            if (e.getDamager() instanceof Player) {
                if (npc.getName().equals(e.getDamager().getName())) {
                    e.setCancelled(true);
                } else {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            npc.destroy();
                        }
                    }.runTaskLater(MiracleBlock.getInstance(), 2L);

                    Location location = npc.getEntity().getLocation();
                    Objects.requireNonNull(location.getWorld()).spawnParticle(Particle.REDSTONE, location, 15, 0.5, 0.5, 0.5,
                            new Particle.DustOptions(Color.fromRGB(205, 255, 247), 2));

                    LivingEntity player = (Player) e.getDamager();
                    player.damage(e.getDamage());
                    player.sendMessage(((Player) e.getDamager()).getName() + ">" + "这 这不可能");
                    player.sendMessage("还是先看看自己的样子吧");
                }
            }
        }
    }
}
