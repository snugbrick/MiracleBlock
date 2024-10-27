package github.snugbrick.miracleblock.gui

import github.snugbrick.miracleblock.MiracleBlock
import github.snugbrick.miracleblock.items.MiraBlockItemStack
import github.snugbrick.miracleblock.recipes.Recipes
import github.snugbrick.miracleblock.tools.NSK
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class MiracleCraftingTable(private val player: Player, private val title: String) : Listener {
    private val SIZE = 45

    init {
        craftingTable = Bukkit.createInventory(player, SIZE, title)
    }

    companion object {
        private lateinit var craftingTable: Inventory
    }

    fun open(player: Player) {
        for (i in 0..SIZE) {
            when (i) {
                11, 12, 13, 19, 28, 37, 22, 31, 40 -> craftingTable.setItem(i, MiraBlockItemStack.getItem("green_slot"))
                17, 25, 27, 35 -> craftingTable.addItem(MiraBlockItemStack.getItem("blue_slot"))
            }

            if (i == 24) craftingTable.addItem(MiraBlockItemStack.getItem("yellow_slot"))
            craftingTable.addItem(MiraBlockItemStack.getItem("black_slot"))
        }
        player.openInventory(craftingTable)
    }

    fun getInven(): Inventory {
        return craftingTable
    }

    private var clickedPlayer: Player? = null

    @EventHandler
    fun whenPlayerClickYellowGlass(e: InventoryClickEvent) {
        if (e.whoClicked is Player) clickedPlayer = e.whoClicked as Player
        else return

        val currentItem = e.currentItem
        if (currentItem != null && NSK.hasNameSpacedKey(currentItem, NamespacedKey(MiracleBlock.getInstance(), "slot"))) {
            if (e.action == InventoryAction.HOTBAR_MOVE_AND_READD || e.action == InventoryAction.HOTBAR_SWAP) {
                e.isCancelled = true
            }
            val whichColor =
                NSK.getNameSpacedKey(currentItem, NamespacedKey(MiracleBlock.getInstance(), "slot"))
            if (whichColor.equals("yellow")) {
                val inputs: Array<MiraBlockItemStack?> = arrayOf(11, 12, 13, 20, 21, 22, 29, 30, 31)
                    .map { index -> e.inventory.getItem(index) as MiraBlockItemStack }
                    .toTypedArray()

                val craft = Recipes.getCraft(inputs)
                if (craft != null) e.inventory.setItem(26, craft)
            }
        }
    }

    @EventHandler
    fun onPlayerCloseInventory(event: InventoryCloseEvent) {
        val player = event.player as Player
        Bukkit.getScheduler()
            .runTaskLater(MiracleBlock.getInstance(), Runnable {
                cleanInventory(player)
                player.updateInventory()
            }, 3L)
    }

    private fun cleanInventory(player: Player) {
        for (currentItem in player.inventory.contents) {
            if (currentItem == null) continue
            if (!NSK.hasNameSpacedKey(
                    currentItem,
                    NamespacedKey(MiracleBlock.getInstance(), "slot")
                )
            ) continue
            player.inventory.remove(currentItem)
        }
        player.updateInventory()
    }
}