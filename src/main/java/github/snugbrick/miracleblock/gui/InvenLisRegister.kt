package github.snugbrick.miracleblock.gui

import github.snugbrick.miracleblock.MiracleBlock
import github.snugbrick.miracleblock.items.InlayItemStack.InlaidGemItemStack
import github.snugbrick.miracleblock.items.MiraBlockItemStack
import github.snugbrick.miracleblock.items.weapon.SwordItemStack
import github.snugbrick.miracleblock.recipes.Recipes
import github.snugbrick.miracleblock.tools.NSK
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InvenLisRegister : Listener {
    @EventHandler
    fun whenPlayerClickYellowGlass(e: InventoryClickEvent) {
        lateinit var clickedPlayer: Player
        if (e.whoClicked is Player) clickedPlayer = e.whoClicked as Player
        else return

        val currentItem = e.currentItem
        if (currentItem != null && NSK.hasNameSpacedKey(currentItem, NamespacedKey(MiracleBlock.getInstance(), "slot"))) {
            e.isCancelled = true
        }
        when (e.view.title) {
            "mirablock crafting table" -> {
                if (e.slot == 26) e.isCancelled = true
                val whichColor = NSK.getNameSpacedKey(currentItem, NamespacedKey(MiracleBlock.getInstance(), "slot"))
                if (whichColor.equals("yellow")) {
                    val inputs: Array<MiraBlockItemStack?> = arrayOf(11, 12, 13, 20, 21, 22, 29, 30, 31)
                        .map { index -> e.inventory.getItem(index) as MiraBlockItemStack }
                        .toTypedArray()

                    val craft = Recipes.getCraft(inputs)
                    if (craft != null) {
                        e.inventory.setItem(26, craft)
                        clickedPlayer.sendMessage("你已合成{${craft.itemMeta?.displayName}}")
                    }
                }
            }

            "mirablock inlay table" -> {
                val whichColor = NSK.getNameSpacedKey(currentItem, NamespacedKey(MiracleBlock.getInstance(), "slot"))
                if (whichColor.equals("yellow")) {
                    val input = e.inventory.getItem(3)
                    val inlay = e.inventory.getItem(7)
                    if (input != null && inlay != null) {
                        val miraInput = MiraBlockItemStack(input)
                        val miraInlay = MiraBlockItemStack(inlay)
                        if (NSK.hasNameSpacedKey(input, NamespacedKey(MiracleBlock.getInstance(), "miracle_sword"))) {
                            val swordItemStack = SwordItemStack(miraInput)
                            for (i in 0..swordItemStack.slotCount) {
                                //如果为null 则代表有东西了 不跳出循环 如果不为null 说明嵌入成功 跳出循环
                                val after = swordItemStack.setInlay(InlaidGemItemStack(miraInlay), i)
                                if (after != null) break
                            }
                        }
                    } else {
                        clickedPlayer.sendMessage("物品缺失 请保证左侧是被镶嵌物 右侧是镶嵌物")
                    }
                }
            }
        }
    }

    //防止f键拿走workbench的物品
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