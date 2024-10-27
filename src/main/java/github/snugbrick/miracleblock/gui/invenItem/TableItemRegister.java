package github.snugbrick.miracleblock.gui.invenItem;

import github.snugbrick.miracleblock.items.MiraBlockItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TableItemRegister {
    public void tableItemRegister() {
        new MiraBlockItemStack(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE),
                "slot", "blue").register("blue_slot");

        new MiraBlockItemStack(new ItemStack(Material.GREEN_STAINED_GLASS_PANE),
                "slot", "green").register("green_slot");

        new MiraBlockItemStack(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE),
                "slot", "yellow").register("yellow_slot");

        new MiraBlockItemStack(new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
                "slot", "black").register("black_slot");
    }
}
