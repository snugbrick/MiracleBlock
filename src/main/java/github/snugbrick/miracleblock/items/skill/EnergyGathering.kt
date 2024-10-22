package github.snugbrick.miracleblock.items.skill

import github.snugbrick.miracleblock.MiracleBlock
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin


class EnergyGathering(
    private val force: Float, //拉弓程度
    private val arrow: Arrow, //箭矢本身
    private val vector: Vector, //方向
    private val addDamage: Double //增加的伤害
) : _Skill {
    private var coldDown: Int = 0
    private val arrowLocation = arrow.location
    private val shooter = arrow.shooter
    private val maxDistance = 160.0
    private val originDamage = arrow.damage
    override fun runTasks() {
        arrow.damage += addDamage
        arrow.setGravity(false);
        if (force == 1F) {
            val result: RayTraceResult? = arrow.world.rayTraceEntities(
                arrow.location,
                vector,
                maxDistance
            )
            if (result?.hitEntity != null && result.hitEntity is LivingEntity) {
                val target = result.hitEntity as LivingEntity
                target.damage(originDamage + addDamage * 2)
            }
            arrow.remove()
        }

        object : BukkitRunnable() {
            override fun run() {
                arrow.remove()
            }
        }.runTaskLater(MiracleBlock.getInstance(), 5 * 20L)
    }

    override fun genParticle() {
        var maxRadius = 2.0
        val count = 2
        var counter = 1
        object : BukkitRunnable() {
            override fun run() {
                if (counter == count) cancel()

                if (arrow.isDead || arrow.isOnGround) {
                    cancel()
                    return
                }

                // 垂直于飞行向量的两个向量
                val perpendicular1 = getPerpendicularVector(vector)
                val perpendicular2: Vector = vector.clone().crossProduct(perpendicular1).normalize()

                // 生成粒子圈
                generateParticleCircle(arrow.location, perpendicular1, perpendicular2, maxRadius);

                maxRadius -= 0.5
                counter++
            }
        }.runTaskTimer(MiracleBlock.getInstance(), 2L, 2L)

        if (force == 1F) {
            val directionOffset = vector.normalize()//.multiply(0.2)
            for (i in 1..maxDistance.toInt() + 1) {
                val everyLocation = arrowLocation.clone().add(directionOffset.multiply(i))
                for (a in 1..31) {
                    everyLocation.world?.spawnParticle(
                        Particle.REDSTONE, everyLocation.add(directionOffset.multiply(1.0 + a / 30)), 0,
                        0.0, 0.0, 0.0, 0.1, Particle.DustOptions(Color.fromRGB(205, 255, 247), 1.0f)
                    )
                }
            }
        }

    }

    override fun playSound() {
    }

    override fun setColdDown(cold_down: Int) {
        this.coldDown = cold_down
    }

    override fun getColdDown(): Int {
        return this.coldDown
    }

    private fun getPerpendicularVector(direction: Vector): Vector {
        return if (direction.x == 0.0 && direction.z == 0.0) {
            Vector(1, 0, 0)
        } else {
            Vector(-direction.z, 0.0, direction.x).normalize()
        }
    }

    // 生成粒子圈
    private fun generateParticleCircle(
        location: Location,
        perpendicular1: Vector,
        perpendicular2: Vector,
        radius: Double
    ) {
        val points = 20
        val angleStep = 2 * Math.PI / points // 粒子间的角度差

        for (i in 0 until points) {
            val angle = i * angleStep
            // 圆上每个点的坐标
            val offset = perpendicular1.clone().multiply(cos(angle)).add(perpendicular2.clone().multiply(sin(angle)))
                .multiply(radius)

            val newVec = vector.normalize().multiply(0.5)
            location.world?.spawnParticle(
                Particle.REDSTONE, location.clone().add(offset), 0,
                newVec.x, newVec.y, newVec.z, 0.1, Particle.DustOptions(Color.fromRGB(205, 255, 247), 1.5f)
            )
        }
    }
}