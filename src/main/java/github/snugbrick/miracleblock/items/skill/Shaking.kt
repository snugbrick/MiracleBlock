package github.snugbrick.miracleblock.items.skill

import github.snugbrick.miracleblock.MiracleBlock
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class Shaking(
    private val player: Player,
    private val kb: Vector,
    private val range: Double,
    private val damage: Double,
    private val vertigoTime: Int
) : _Skill {
    private var coldDown: Int = 0
    var entity: Entity? = null
    override fun runTasks() {
        val location = player.location

        val nearbyEntities: List<Entity> = player.getNearbyEntities(location.x, location.y, location.z)
        for (entity in nearbyEntities) {
            this.entity = entity

            if (this.entity != null) {
                val distance = location.distance(this.entity!!.location)
                val targetVector = this.entity!!.location.toVector()
                if (distance <= range) {
                    val livingEntity = this.entity as LivingEntity

                    livingEntity.damage(damage);
                    livingEntity.addPotionEffect(PotionEffect(PotionEffectType.SLOW, vertigoTime * 20, 1, true, false))
                    livingEntity.addPotionEffect(
                        PotionEffect(
                            PotionEffectType.BLINDNESS,
                            vertigoTime * 20,
                            1,
                            true,
                            false
                        )
                    )
                    val player2AimDirection = targetVector.subtract(player.location.toVector()).normalize()
                    livingEntity.velocity = player2AimDirection.crossProduct(kb)
                }
            }
        }
    }

    override fun genParticle() {
        object : BukkitRunnable() {
            override fun run() {
                val dust = DustOptions(Color.fromBGR(179, 109, 65), 1F)
                player.world.spawnParticle(Particle.REDSTONE, player.location, 45, range, 0.0, range, dust)
            }
        }.runTask(MiracleBlock.getInstance())
    }

    override fun playSound() {
        player.world.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1F, 0F);
    }

    override fun setColdDown(cold_down: Int) {
        this.coldDown = cold_down
    }

    override fun getColdDown(): Int {
        return this.coldDown
    }

    companion object {
        val thisPlayer: Player? = null

        fun printHi() {
            println("Hi")
        }//	20240927215025GmGZZ订单号
    }
}