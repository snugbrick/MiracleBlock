package github.snugbrick.miracleblock.entity.monster.boss

import net.citizensnpcs.api.CitizensAPI
import org.bukkit.Location
import org.bukkit.entity.EntityType


class SecondBinaryStar {
    companion object {
        fun spawnNPC(name: String, spawnLocation: Location) {
            val registry = CitizensAPI.getNPCRegistry()
            val npc = registry.createNPC(EntityType.PLAYER, name)

            npc?.addTrait(SecondBinaryStarTrait())
            npc.spawn(spawnLocation)
        }
    }
}