package github.snugbrick.miracleblock.items.weapon.command;

import github.snugbrick.miracleblock.MiracleBlock;
import github.snugbrick.miracleblock.items.MainItemStack;
import github.snugbrick.miracleblock.items.weapon.SwordItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Deprecated
public class SetInlaidCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("set-item-inlaid") && sender instanceof Player) {
            switch (args[0]) {
                case "let-into":
                    ItemStack itemStack = ((Player) sender).getInventory().getItemInMainHand();
                    MainItemStack miracleBlockItemStack = new MainItemStack(itemStack);
                    SwordItemStack swordItemStack = new SwordItemStack(miracleBlockItemStack);
                    MiracleBlock.getInstance().getLogger().info(swordItemStack.toString());
                    //swordItemStack.setInlay();
                    break;
                case "take-out":
                    sender.sendMessage(MainItemStack.getAllMiracleBlockItemStack().toString());
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
