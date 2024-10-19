package github.snugbrick.miracleblock.items.skill;

import com.mojang.authlib.GameProfile;
import de.myzelyam.api.vanish.VanishAPI;
import github.snugbrick.miracleblock.MiracleBlock;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.npc.skin.Skin;
import net.citizensnpcs.npc.skin.SkinPacketTracker;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Illusion implements _Skill {
    private int cold_down;
    private final Player player;
    private NPC npc;
    private final int time;

    public Illusion(Player player, int time) {
        this.player = player;
        this.time = time;
    }

    @Override
    public void runTasks() {
        //CitizensNPC
        //npc.getEntity()
        NPCRegistry registry = CitizensAPI.getNamedNPCRegistry(player.getName());
        if (registry == null) {
            registry = CitizensAPI.getNPCRegistry();
        }
        npc = registry.createNPC(EntityType.PLAYER, player.getName());

        if (Skin.get(player.getName(), true).isValid()) {
            Skin.get(player.getName(), true).apply(new FakePlayer());
        }

        //npc.setProtected(false);
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, player.getInventory().getItemInMainHand());
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, player.getInventory().getItemInOffHand());
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, player.getInventory().getHelmet());
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, player.getInventory().getChestplate());
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, player.getInventory().getLeggings());
        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, player.getInventory().getBoots());

        if (npc.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) npc.getEntity();
            entity.setCollidable(false);
        }

        npc.spawn(player.getLocation());
        new BukkitRunnable() {
            @Override
            public void run() {
                npc.destroy();
            }
        }.runTaskLater(MiracleBlock.getInstance(), time * 20L);

        if (!VanishAPI.getInvisiblePlayers().contains(player.getUniqueId())) {
            player.setInvisible(true);
            setInvis(player, true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setInvisible(false);
                    setInvis(player, false);
                }
            }.runTaskLater(MiracleBlock.getInstance(), time * 20L);
        }
    }

    public void setInvis(Player player, boolean aim) {
        //List<UUID> invisiblePlayers = VanishAPI.getInvisiblePlayers();
        if (aim) {
            VanishAPI.hidePlayer(player);
        } else {
            VanishAPI.showPlayer(player);
        }
    }

    @Override
    public void genParticle() {
        Location triggerLoc = player.getLocation();
        player.getWorld().spawnParticle(Particle.REDSTONE, triggerLoc, 15, 0.5, 1, 0.5,
                new Particle.DustOptions(Color.fromRGB(205, 255, 247), 2));
    }

    @Override
    public void playSound() {

    }

    @Override
    public void setColdDown(int cold_down) {
        this.cold_down = cold_down;
    }

    @Override
    public int getColdDown() {
        return cold_down;
    }

    public class FakePlayer implements SkinnableEntity {
        @Override
        public Player getBukkitEntity() {
            return player;
        }

        @Override
        public GameProfile getProfile() {
            return new GameProfile(player.getUniqueId(), player.getName());
        }

        @Override
        public String getSkinName() {
            return player.getName();
        }

        @Override
        public SkinPacketTracker getSkinTracker() {
            return null;
        }

        @Override
        public void setSkinFlags(byte b) {
        }

        @Override
        public void setSkinName(String s) {
        }

        @Override
        public void setSkinName(String s, boolean b) {
        }

        @Override
        public void setSkinPersistent(String s, String s1, String s2) {
        }

        @Override
        public NPC getNPC() {
            return npc;
        }
    }
}
