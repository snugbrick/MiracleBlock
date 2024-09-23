package github.snugbrick.miracleblock.entity.monster;


import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import java.util.EnumSet;
import java.util.List;

//[02:36:24 ERROR]: No data fixer registered for miracle_entity:miracle_slime
public class MiracleEntitySlime extends EntitySlime {
    public MiracleEntitySlime(EntityTypes<? extends EntitySlime> var0, World var1) {
        super(var0, var1);
        //删除原有ai
        //this.goalSelector = new PathfinderGoalSelector(world.getMethodProfilerSupplier());
        //this.targetSelector = new PathfinderGoalSelector(world.getMethodProfilerSupplier());
        this.initAI();
    }

    public MiracleEntitySlime(Location loc) {
        this(EntityTypes.SLIME, ((CraftWorld) loc.getWorld()).getHandle());
        setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    private void initAI() {
        this.goalSelector.a(0, new SlimeDash2Player(this));
    }

    public static void registerSlime() throws Exception {
        try {
            EntityTypes<?> slimeType = EntityTypes.Builder.a(MiracleEntitySlime::new, EnumCreatureType.MONSTER)
                    .a(1.0F, 1.0F)
                    .a(new MinecraftKey("miracle_entity", "miracle_slime").toString());

            //a->registry
            IRegistry.a(IRegistry.ENTITY_TYPE, new MinecraftKey("miracle_entity", "miracle_slime"), slimeType);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    static class SlimeDash2Player extends PathfinderGoal {
        private final MiracleEntitySlime miracleSlime;
        private EntityPlayer targetPlayer;
        private int cooldownTimer = 0;
        private static final int COOLDOWN_TIME = 100;// 5s冷却

        public SlimeDash2Player(MiracleEntitySlime entitySlime) {
            this.miracleSlime = entitySlime;
            this.a(EnumSet.of(Type.MOVE, Type.LOOK));
            entitySlime.getNavigation().d(true);//导航控制器 true->允许水中的视线搜索
        }

        @Override
        public boolean a() {// canUse()
            if (cooldownTimer > 0) {
                cooldownTimer--;
                return false;
            }
            List<EntityPlayer> nearbyPlayers = this.miracleSlime.world.a(EntityPlayer.class,
                    this.miracleSlime.getBoundingBox().grow(10.0D, 10.0D, 10.0D));

            for (EntityPlayer player : nearbyPlayers) {
                if (player.isAlive() && !player.abilities.canInstantlyBuild) {
                    this.targetPlayer = player;
                    return true;
                }
            }
            return false;
        }

        @Override
        public void c() {// start()
            super.c();
            //看向玩家
            this.miracleSlime.a(this.targetPlayer, 10.0F, 10.0F); // a()可能为lookAt
        }

        @Override
        public void d() { // stop()
            super.d();
            this.cooldownTimer = COOLDOWN_TIME;
        }

        @Override
        public boolean b() { // canContinueToUse()
            return this.targetPlayer != null && this.targetPlayer.isAlive();
        }

        @Override
        public void e() { // tick()
            if (this.targetPlayer != null) {
                //朝向玩家
                this.miracleSlime.a(this.targetPlayer, 10.0F, 10.0F);

                Vec3D direction = new Vec3D(this.targetPlayer.locX() - this.miracleSlime.locX(),
                        this.targetPlayer.locY() - this.miracleSlime.locY(),
                        this.targetPlayer.locZ() - this.miracleSlime.locZ());
                direction = direction.d(); // d()是normalize()

                //飞向玩家
                double speed = 0.5D;
                this.miracleSlime.e(direction.x * speed, direction.y * speed, direction.z * speed); // e()是setDeltaMovement()
            }
        }

        public EntityPlayer getTargetPlayer() {
            return targetPlayer;
        }

    }
}
