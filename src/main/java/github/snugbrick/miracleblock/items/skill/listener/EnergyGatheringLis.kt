package github.snugbrick.miracleblock.items.skill.listener

import github.snugbrick.miracleblock.api.event.LaserHitEvent
import github.snugbrick.miracleblock.items.skill.EnergyGathering
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent


class EnergyGatheringLis : Listener {
    @EventHandler
    fun onPlayerShoot(e: EntityShootBowEvent) {
        if (e.entity is Player) {
            EnergyGathering(e.force, e.projectile as Arrow, e.projectile.velocity.normalize(), 7.0).run()
        }
    }
}