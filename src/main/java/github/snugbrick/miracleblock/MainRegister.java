package github.snugbrick.miracleblock;

import github.snugbrick.miracleblock.items.InlayItemStack.InlayItemRegister;
import github.snugbrick.miracleblock.items.weapon.SwordRegister;
import github.snugbrick.miracleblock.table.inven.TableItemRegister;

public class MainRegister {
    public void mainReg() {
        new SwordRegister().swordRegister();
        new TableItemRegister().tableItemRegister();
        new InlayItemRegister().inlayItemRegister();
    }
}
