package github.snugbrick.miracleblock.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2024.08.27 00:57
 */
@Deprecated
public class ToTemplateWorld implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("totemplateworld") && sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage("您不具有op权限");
                return false;
            }
            World world = Bukkit.getWorld("template_world");
            if (world != null) ((Player) sender).teleport(world.getSpawnLocation());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
