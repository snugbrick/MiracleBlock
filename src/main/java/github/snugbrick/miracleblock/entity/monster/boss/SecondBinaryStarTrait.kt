package github.snugbrick.miracleblock.entity.monster.boss

import net.citizensnpcs.api.ai.event.CancelReason
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter
import net.citizensnpcs.api.ai.tree.BehaviorStatus
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.persistence.Persist
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.api.util.DataKey
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

//好看的白子图片 https://twitter.com/T1kosewad/status/1629760675022278656 513538
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
        lateinit var nearbyEntities: List<Entity>
        if (npc.entity != null) {
            nearbyEntities = npc.entity.getNearbyEntities(10.0, 10.0, 10.0)
            npc.defaultGoalController.addBehavior(Walk2PlayerGoal(npc), 1)

            for (entity in nearbyEntities) {
                npc.entity.location.distance(entity.location)
            }
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

    class Walk2PlayerGoal(private val npc: NPC) : BehaviorGoalAdapter() {
        constructor(npc: NPC, aimEntity: Entity) : this(npc)

        private var reason: CancelReason? = null
        private var finished = false

        override fun reset() {
            npc.navigator.cancelNavigation()
            this.reason = null
            finished = false
        }

        override fun run(): BehaviorStatus {
            return if (finished) {
                if (this.reason == null) BehaviorStatus.SUCCESS else BehaviorStatus.FAILURE
            } else {
                BehaviorStatus.RUNNING
            }
        }

        override fun shouldExecute(): Boolean {
            val executing = !npc.navigator.isNavigating
            if (executing && findClosestPlayer(npc.entity) != null) {
                npc.navigator.setTarget(findClosestPlayer(npc.entity)?.location)
                npc.navigator.localParameters.addSingleUseCallback { cancelReason ->
                    finished = true
                    reason = cancelReason
                }
            }
            return executing
        }

        private fun findClosestPlayer(npcEntity: Entity): Player? {
            return Bukkit.getOnlinePlayers()
                .filter { it.location.distance(npcEntity.location) <= 3.0 }
                .minByOrNull { it.location.distance(npcEntity.location) }
        }
    }
    /*
        fun setupTree(npc: NPC) {
            npc.defaultGoalController
                .addGoal(Sequence.createSequence(MyBehavior(), MyAccumulateBehavior(), MyParallelBehavior()))

            // A more complicated example
            npc.getGoalController().addGoal(
                Sequence.createSequence(
                    IfElse(
                        Condition { npc.isCool() },
                        TimerDecorator.tickLimiter(MyLongRunningBehavior(), 100),
                        MyElseBehavior()
                    ),
                    MyParallelBehavior()
                )
            )

            // You can implement nested loops and other behavior sequences using the API provided in net.citizensnpcs.api.ai.tree
        }
         */
}