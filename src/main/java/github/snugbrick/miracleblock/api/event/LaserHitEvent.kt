package github.snugbrick.miracleblock.api.event

import org.bukkit.entity.Entity
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * called when Entity hit by Laser from an Entity
 */
class LaserHitEvent(
    private val shooter: Entity,
    private val hitEntity: Entity,
    private val damage: Double
) : Event() {
    private val handlers = HandlerList()
    override fun getHandlers(): HandlerList {
        return handlers
    }

    fun getShooter(): Entity {
        return shooter
    }

    fun getHitEntity(): Entity {
        return hitEntity
    }

    fun getDamage(): Double {
        return damage
    }
}