package github.snugbrick.miracleblock.items

import github.snugbrick.miracleblock.items.inlayItemStack.InlaidGemItemStack
import github.snugbrick.miracleblock.items.weapon.SwordItemStack
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class UseSkillListener : Listener {
    @EventHandler
    fun onUseSkill(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val player = event.damager as Player
            val mirabItemInMainHand = MiraBlockItemStack(player.inventory.itemInMainHand)
            if (SwordItemStack.isSwordItemStack(mirabItemInMainHand)) {
                val swordItemStack = SwordItemStack(mirabItemInMainHand)
                val inlaidGemItemStack: Array<InlaidGemItemStack> = swordItemStack.inlaidGemItemStack

                //TODO: get player using inlaid gem

            }
        }
    }
}