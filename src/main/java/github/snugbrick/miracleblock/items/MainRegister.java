package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.gui.invenItem.TableItemRegister;
import github.snugbrick.miracleblock.items.inlayItemStack.InlayItemRegister;
import github.snugbrick.miracleblock.items.inlayItemStack.SkillInlayRegister;
import github.snugbrick.miracleblock.items.weapon.SwordRegister;

public class MainRegister {
    public void mainReg() {
        new SwordRegister().swordRegister();
        new TableItemRegister().tableItemRegister();
        new InlayItemRegister().inlayItemRegister();
        new SkillInlayRegister().skillInlayRegister();
    }
}
