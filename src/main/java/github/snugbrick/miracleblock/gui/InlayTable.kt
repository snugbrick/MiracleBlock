package github.snugbrick.miracleblock.gui

import github.snugbrick.miracleblock.items.MiraBlockItemStack
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class InlayTable(private val player: Player, private val title: String) {
    private val SIZE = 9

    init {
        craftingTable = Bukkit.createInventory(player, SIZE, title)
    }

    companion object {
        private lateinit var craftingTable: Inventory
    }

    fun open(player: Player) {
        for (i in 0..SIZE) {
            when (i) {
                1, 2, 4 -> craftingTable.setItem(i, MiraBlockItemStack.getItem("green_slot"))
                6, 8, 9 -> craftingTable.addItem(MiraBlockItemStack.getItem("blue_slot"))
            }

            if (i == 5) craftingTable.addItem(MiraBlockItemStack.getItem("yellow_slot"))
            craftingTable.addItem(MiraBlockItemStack.getItem("black_slot"))
        }
        player.openInventory(craftingTable)
    }
}