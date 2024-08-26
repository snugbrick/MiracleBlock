package github.snugbrick.miracleblock.Listener;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.25 17:07
 */
public class NewPlayerRoomGenLis implements Listener {
    private UUID PlayerUUID;
    private Player player;
    private World world;

    @EventHandler
    public void newPlayerJoinLis(PlayerJoinEvent event) {
        player = event.getPlayer();
        PlayerUUID = player.getUniqueId();

        WorldCreator playerRooms = new WorldCreator("_the_world_of_" + PlayerUUID);
        World world = Bukkit.getWorld("template_world");
        if (world != null) playerRooms.copy(world);
        this.world = playerRooms.createWorld();

        if (!player.hasPlayedBefore()) copyStructures(world, this.world, new Location(this.world, 0, 0, 0));

        this.world.setSpawnLocation(0, 62, 0);
        if (this.world != null) player.teleport(this.world.getSpawnLocation());
        player.addPotionEffect(new PotionEffect((PotionEffectType.NIGHT_VISION), 36000 * 500, 1));
    }

    @EventHandler
    public void PlayerDeadSendBack(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getDamage() >= ((Player) e.getEntity()).getHealth()) {
            e.setCancelled(true);
            e.getEntity().teleport(world.getSpawnLocation());
            ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
        }
    }

    public void copyStructures(World sourceWorld, World targetWorld, Location center) {
        int chunkX = center.getBlockX() >> 4;
        int chunkZ = center.getBlockZ() >> 4;

        for (int x = chunkX - 1; x <= chunkX + 1; x++) {
            for (int z = chunkZ - 1; z <= chunkZ + 1; z++) {
                Chunk sourceChunk = sourceWorld.getChunkAt(x, z);
                Chunk targetChunk = targetWorld.getChunkAt(x, z);

                sourceChunk.load();
                targetChunk.load();

                for (int cx = 0; cx < 16; cx++) {
                    for (int cz = 0; cz < 16; cz++) {
                        for (int y = 50; y <= 80; y++) {
                            Block sourceBlock = sourceChunk.getBlock(cx, y, cz);
                            Block targetBlock = targetChunk.getBlock(cx, y, cz);

                            if (sourceBlock.getType() != Material.AIR) {
                                targetBlock.setType(sourceBlock.getType());

                                BlockState sourceState = sourceBlock.getState();
                                if (sourceState instanceof TileState) {
                                    targetBlock.getState().update(true, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
