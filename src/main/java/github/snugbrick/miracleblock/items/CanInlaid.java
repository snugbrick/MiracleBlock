package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.items.inlayItemStack.InlayGemItemStack;
import github.snugbrick.miracleblock.items.weapon.SwordItemStack;

import java.util.Iterator;

/**
 * 可镶嵌的
 */
public interface CanInlaid {
    SwordItemStack setInlay(InlayGemItemStack inlaidGemItemStack, int indexSlot);
    //for stream
    SwordItemStack setInlay(Iterator<InlayGemItemStack> itemStacks);
}
