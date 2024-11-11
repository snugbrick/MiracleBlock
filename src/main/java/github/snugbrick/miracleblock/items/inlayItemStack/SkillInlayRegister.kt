package github.snugbrick.miracleblock.items.inlayItemStack

import github.snugbrick.miracleblock.items.ItemAttribute
import github.snugbrick.miracleblock.items.ItemLevel
import github.snugbrick.miracleblock.items.skill._SkillStack
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SkillInlayRegister {
    fun skillInlaidRegister() {
        InlayGemItemStack(
            ItemStack(Material.DIAMOND), "miracle-skill-inlay", "WayOut",
            ItemAttribute.WOOD, ItemLevel.A, _SkillStack.getAbility("WayOut")
        ).register("way_out")

        InlayGemItemStack(
            ItemStack(Material.DIAMOND), "miracle-skill-inlay", "Shaking",
            ItemAttribute.WOOD, ItemLevel.A, _SkillStack.getAbility("Shaking")
        ).register("shaking")
    }
}