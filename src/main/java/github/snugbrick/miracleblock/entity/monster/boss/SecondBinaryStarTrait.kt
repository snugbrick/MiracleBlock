package github.snugbrick.miracleblock.entity.monster.boss

import net.citizensnpcs.api.persistence.Persist
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.api.util.DataKey
import org.bukkit.Bukkit


class SecondBinaryStarTrait(name: String? = "The_Second_Binary_Star") : Trait(name) {
    var testSetting = false

    @Persist("The_Second_Binary_Star")
    var automaticallyPersistedSetting = false
    override fun load(key: DataKey) {
        testSetting = key.getBoolean("SomeSetting", false)
    }

    override fun save(key: DataKey) {
        key.setBoolean("SomeSetting", testSetting)
    }


    // Called every tick
    override fun run() {
        val nearbyEntities = npc.entity.getNearbyEntities(3.0, 3.0, 3.0)
        for (entity in nearbyEntities) {
            npc.entity.location.distance(entity.location)
        }
        if (npc != null) {
            npc.defaultGoalController.addBehavior(StarIdleGoal(npc), 1);
        }
    }

    //Run code when your trait is attached to a NPC.
    //This is called BEFORE onSpawn, so npc.getEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
    override fun onAttach() {
        Bukkit.getServer().logger.info("${npc.name} has been assigned SecondBinaryStarTrait!")

        //if (npc != null) {
        //npc.defaultGoalController.addBehavior(StarIdleGoal(npc), 1);
        //}
    }

    // Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getEntity() is still valid.
    override fun onDespawn() {}

    //Run code when the NPC is spawned. Note that npc.getEntity() will be null until this method is called.
    //This is called AFTER onAttach and AFTER Load when the server is started.
    override fun onSpawn() {

    }

    //run code when the NPC is removed. Use this to tear down any repeating tasks.
    override fun onRemove() {}
}