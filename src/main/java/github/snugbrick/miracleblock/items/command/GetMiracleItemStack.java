package github.snugbrick.miracleblock.items.command;

import github.snugbrick.miracleblock.items.MainItemStack;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class GetMiracleItemStack implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("get-miracle-item") && sender instanceof Player) {
            ((Player) sender).getInventory().addItem(MainItemStack.getItem(args[0]));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        return null;
    }
}
