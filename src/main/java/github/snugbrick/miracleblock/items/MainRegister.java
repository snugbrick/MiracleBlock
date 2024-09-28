package github.snugbrick.miracleblock.items;

import github.snugbrick.miracleblock.items.InlayItemStack.InlayItemRegister;
import github.snugbrick.miracleblock.items.weapon.SwordRegister;

public class MainRegister {
    public void mainReg() {
        new SwordRegister().swordRegister();
        new InlayItemRegister().inlayItemRegister();
    }
}
