package github.snugbrick.miracleblock.items.skill.listener;

import github.snugbrick.miracleblock.MiracleBlock;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Illusion implements Listener {
    @EventHandler
    public void playerHitFakePlayer(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof SkinnableEntity) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(e.getEntity());

            boolean lock = true;
            if (e.getEntity() instanceof Player) {
                if (npc.getName().equals(e.getEntity().getName())) {
                    lock = false;
                    e.setCancelled(true);
                }
                if (lock) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            npc.destroy();
                        }
                    }.runTaskLater(MiracleBlock.getInstance(), 2L);

                    Location location = npc.getEntity().getLocation();
                    location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 0, 1, 1, 1, 0.5);

                    LivingEntity player = (Player) e.getDamager();
                    player.damage(e.getDamage());
                    player.sendMessage(((Player) e.getDamager()).getName() + ">" +"这 这不可能");
                    player.sendMessage("还是先看看自己的样子吧");
                }
            }

        }
    }
}
