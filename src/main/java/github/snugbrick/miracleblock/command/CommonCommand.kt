package github.snugbrick.miracleblock.command

import github.snugbrick.miracleblock.api.event.Player2IslandEvent
import github.snugbrick.miracleblock.entity.monster.boss.SecondBinaryStar.Companion.spawnNPC
import github.snugbrick.miracleblock.gui.InlayTable
import github.snugbrick.miracleblock.gui.MiracleCraftingTable
import github.snugbrick.miracleblock.items.MiraBlockItemStack
import github.snugbrick.miracleblock.items.inlayItemStack.InlayGemItemStack
import github.snugbrick.miracleblock.items.weapon.SwordItemStack
import github.snugbrick.miracleblock.items.weapon.WeaponItemWords
import github.snugbrick.miracleblock.tools.Debug
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class CommonCommand : TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("mb", ignoreCase = true) && sender is Player) {
            if (args[0].equals(null)) sender.sendMessage("null args, usage -> /mb <args>")
            val itemStack = MiraBlockItemStack(sender.inventory.itemInMainHand)

            when (args[0]) {
                "template-world" -> {
                    val world: World? = Bukkit.getWorld("template_world")
                    if (world != null) sender.teleport(world.spawnLocation)
                }

                "home" -> Bukkit.getServer().pluginManager.callEvent(Player2IslandEvent(sender))
                "entity" -> {
                    if (args.size < 2) sender.sendMessage("null args, usage -> /mb entity <entity-name>")
                    when (args[1]) {
                        "second_binary_star" -> spawnNPC("Second_Binary_Star", sender.location, "MiracleUR_PtII")
                    }
                }

                "item" -> {
                    if (args.size < 2) sender.sendMessage("null args, usage -> /mb item <item-name> or /mb item all-items")
                    val item = MiraBlockItemStack.getItem(args[1])
                    if (item != null) sender.inventory.addItem(item) else sender.sendMessage("你未输入所需要物品或者输入的物品不存在")
                    if (args[1] == "all-items") MiraBlockItemStack.getAllMiracleBlockItemStack()
                        .forEach { item.itemMeta?.let { it -> sender.sendMessage(it.displayName) } }
                }

                "inlay" -> {
                    if (args.size < 4)
                        sender.sendMessage("null args, usage -> /mb inlay <in/out> <gem> <slot>")
                    when (args[1]) {
                        "in" ->
                            SwordItemStack(itemStack).setInlay(InlayGemItemStack.getItem(args[2]) as InlayGemItemStack, args[3].toInt())

                        "out" ->
                            SwordItemStack(itemStack).removeInlay(args[2].toInt())
                    }
                }

                "setItemWords" -> {
                    val swordItemStack = SwordItemStack(itemStack)
                    if (SwordItemStack.isSwordItemStack(swordItemStack)) {
                        val randomItemWords = WeaponItemWords.getRandomItemWords(swordItemStack)
                        //
                        Debug(0, "randomItemWords: $randomItemWords")
                        swordItemStack.setItemWords(randomItemWords).buildSword()
                        swordItemStack.addGain()
                        sender.sendMessage("已更改为 $randomItemWords")
                    }
                    sender.inventory.setItemInMainHand(swordItemStack)
                }

                "gui" -> {
                    if (args.size < 2) sender.sendMessage("null args, usage -> /mb gui <gui-name>")
                    when (args[1]) {
                        "crafting-table" -> MiracleCraftingTable(sender, "mirablock crafting table").open(sender)
                        "inlay-table" -> InlayTable(sender, "mirablock inlay table").open(sender)
                    }
                }
            }
        }
        return true
    }
}