package github.snugbrick.miracleblock.items.inlayItemStack

import github.snugbrick.miracleblock.items.ItemAttribute
import github.snugbrick.miracleblock.items.ItemLevel
import github.snugbrick.miracleblock.items.skill._SkillStack
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SkillInlayRegister {
    fun skillInlaidRegister() {
        InlaidGemItemStack(
            ItemStack(Material.DIAMOND), "miracle-skill-inlay", "WayOut",
            ItemAttribute.DARK_FLAME, ItemLevel.A,
            _SkillStack.getAbility("WayOut")
        ).register("way_out")
    }
}