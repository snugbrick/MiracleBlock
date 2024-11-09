package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.items.inlayItemStack.InlaidGemItemStack;
import github.snugbrick.miracleblock.items.weapon.SwordItemStack;

import java.util.Iterator;

/**
 * 可镶嵌的
 */
public interface CanInlaid {
    SwordItemStack setInlay(InlaidGemItemStack inlaidGemItemStack, int indexSlot);
    //for stream
    SwordItemStack setInlay(Iterator<InlaidGemItemStack> itemStacks);
}
