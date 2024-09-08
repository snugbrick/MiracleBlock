package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.items.weapon.InlaidGemItemStack;
import github.snugbrick.miracleblock.items.weapon.SwordItemStack;

/**
 * 可镶嵌的
 */
public interface CanInlaid {
    SwordItemStack setInlay(InlaidGemItemStack inlaidGemItemStack, int indexSlot);
}
