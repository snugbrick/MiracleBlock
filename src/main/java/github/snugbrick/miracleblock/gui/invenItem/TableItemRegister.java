package github.snugbrick.miracleblock.gui.invenItem;

import github.snugbrick.miracleblock.items.MainItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TableItemRegister {
    public void tableItemRegister() {
        new MainItemStack(new ItemStack(Material.BLUE_STAINED_GLASS),
                "slot", "blue").register("blue_slot");

        new MainItemStack(new ItemStack(Material.GREEN_STAINED_GLASS),
                "slot", "green").register("green_slot");

        new MainItemStack(new ItemStack(Material.YELLOW_STAINED_GLASS),
                "slot", "yellow").register("yellow_slot");

        new MainItemStack(new ItemStack(Material.BLACK_STAINED_GLASS),
                "slot", "black").register("black_slot");
    }
}
