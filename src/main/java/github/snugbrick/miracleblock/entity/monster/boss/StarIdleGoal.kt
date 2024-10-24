package github.snugbrick.miracleblock.entity.monster.boss

import net.citizensnpcs.api.ai.event.CancelReason
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter
import net.citizensnpcs.api.ai.tree.BehaviorStatus
import net.citizensnpcs.api.npc.NPC
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

//好看的白子图片 https://twitter.com/T1kosewad/status/1629760675022278656 513538
class StarIdleGoal(private val npc: NPC) : BehaviorGoalAdapter() {
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