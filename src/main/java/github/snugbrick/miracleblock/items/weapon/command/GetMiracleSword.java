package github.snugbrick.miracleblock.items.weapon.command;

import github.snugbrick.miracleblock.items.MiracleBlockItemStack;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class GetMiracleSword implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("get-miracle-sword") && sender instanceof Player) {
            switch (args[0]) {
                case "dull_sword":
                    ((Player) sender).getInventory().addItem(MiracleBlockItemStack.getItem("dull_sword"));
                    break;
                case "query":
                    sender.sendMessage(MiracleBlockItemStack.getAllMiracleBlockItemStack().toString());
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        return null;
    }
}
