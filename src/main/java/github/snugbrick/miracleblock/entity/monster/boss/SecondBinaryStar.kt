package github.snugbrick.miracleblock.entity.monster.boss

import github.snugbrick.miracleblock.tools.SpawnNPC
import org.bukkit.Location


class SecondBinaryStar {
    companion object {
        fun spawnNPC(name: String, spawnLocation: Location, skinName: String) {
            SpawnNPC.spawnNPC(spawnLocation, name, skinName, SecondBinaryStarTrait(), false)
        }
    }
}