package github.snugbrick.miracleblock.gui

import github.snugbrick.miracleblock.items.MiraBlockItemStack
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class MiracleCraftingTable(private val player: Player, private val title: String) : TableInventory {
    private val SIZE = 45

    init {
        craftingTable = Bukkit.createInventory(player, SIZE, title)
    }

    companion object {
        private lateinit var craftingTable: Inventory
    }

    override fun open(player: Player) {
        for (i in 0..SIZE) {
            when (i) {
                11, 12, 13, 19, 28, 37, 22, 31, 40 -> craftingTable.setItem(i, MiraBlockItemStack.getItem("green_slot"))
                17, 25, 27, 35 -> craftingTable.setItem(i, MiraBlockItemStack.getItem("blue_slot"))
            }

            if (i == 24) craftingTable.addItem(MiraBlockItemStack.getItem("yellow_slot"))
            craftingTable.addItem(MiraBlockItemStack.getItem("black_slot"))
        }
        player.openInventory(craftingTable)
    }
}